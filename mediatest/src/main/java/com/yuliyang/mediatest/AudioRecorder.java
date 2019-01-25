package com.yuliyang.mediatest;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaRecorder;
import android.media.audiofx.AcousticEchoCanceler;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author lavender
 * @createtime 2018/5/22
 * @company
 * @desc
 */

public class AudioRecorder {

    private static final String TAG = "AudioRecorder";
    private int SAMPLE_RATE = 44100; //采样率
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO; //音频通道(单声道)
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT; //音频格式
    private static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;  //音频源（麦克风）
    private String encodeType = MediaFormat.MIMETYPE_AUDIO_AAC;
    private boolean is_recording = false;
    private static final int samples_per_frame = 2048;

    public static AudioRecord audioRecord;
    private Thread recorderThread;

    private static AcousticEchoCanceler canceler;//回声消除

    private MediaCodec mediaEncode;
    private MediaCodec.BufferInfo encodeBufferInfo;
    private ByteBuffer[] encodeInputBuffers;
    private ByteBuffer[] encodeOutputBuffers;
    private byte[] chunkAudio = new byte[0];
    private File f = new File(Environment.getExternalStorageDirectory(), "MyAudio.aac");
    private BufferedOutputStream out;
    private RecorderTask recorderTask;


    public AudioRecorder() {
        initAACMediaEncode();
        recorderTask = new RecorderTask();
    }

    /*
        开始录音
     */
    public void startAudioRecording() {
        recorderThread = new Thread(recorderTask);
        if (!recorderThread.isAlive()) {
            recorderThread.start();
        }

    }

    /*
        停止录音
     */
    public void stopAudioRecording() {
        //释放回声消除器
        setAECEnabled(false);

        is_recording = false;

        try {
            mediaEncode.stop();
            mediaEncode.release();
            initAACMediaEncode();
        } catch (IllegalStateException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加AEC回声消除
     *
     * @return
     */
    public static boolean chkNewDev() {
        return android.os.Build.VERSION.SDK_INT >= 16;
    }


    public static boolean isDeviceSupport() {
        return AcousticEchoCanceler.isAvailable();
    }


    public static boolean initAEC(int audioSession) {
        if (canceler != null) {
            return false;
        }
        canceler = AcousticEchoCanceler.create(audioSession);
        if (canceler != null) {
            canceler.setEnabled(true);
        }
        return canceler.getEnabled();
    }

    public static boolean setAECEnabled(boolean enable) {
        if (null == canceler) {
            return false;
        }
        canceler.setEnabled(enable);
        return canceler.getEnabled();
    }


    class RecorderTask implements Runnable {
        int bufferReadResult = 0;


        public RecorderTask() {
            try {
                out = new BufferedOutputStream(new FileOutputStream(f, false));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() {
            //获取最小缓冲区大小
            int bufferSizeInBytes = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);
            Log.e("bufferSizeInBytes*****", bufferSizeInBytes + "");
//            if (chkNewDev()) {
//                //有回声消除
//                audioRecord = new AudioRecord(MediaRecorder.AudioSource.VOICE_COMMUNICATION,
//                        SAMPLE_RATE,
//                        CHANNEL_CONFIG,
//                        AUDIO_FORMAT,
//                        bufferSizeInBytes * 4);
//            } else {
            audioRecord = new AudioRecord(
                    AUDIO_SOURCE,   //音频源
                    SAMPLE_RATE,    //采样率
                    CHANNEL_CONFIG,  //音频通道
                    AUDIO_FORMAT,    //音频格式\采样精度
                    bufferSizeInBytes * 4//缓冲区
            );
//            }
//            if (isDeviceSupport()) {
//                initAEC(audioRecord.getAudioSessionId());
//            }
            audioRecord.startRecording();
            is_recording = true;


            while (is_recording) {
                byte[] buffer = new byte[samples_per_frame];

                //从缓冲区中读取数据，存入到buffer字节数组数组中
                bufferReadResult = audioRecord.read(buffer, 0, buffer.length);
                //判断是否读取成功
                if (bufferReadResult == AudioRecord.ERROR_BAD_VALUE || bufferReadResult == AudioRecord.ERROR_INVALID_OPERATION)
                    Log.e(TAG, "Read error");
                if (audioRecord != null && bufferReadResult > 0) {
                    Log.i("bufferReadResult----->", bufferReadResult + "");


                    try {
                        dstAudioFormatFromPCM(buffer);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }


                }

            }
            if (audioRecord != null) {

                audioRecord.setRecordPositionUpdateListener(null);
                audioRecord.stop();
                audioRecord.release();
                audioRecord = null;

            }


        }
    }


    /**
     * 初始化AAC编码器
     */
    private void initAACMediaEncode() {
        try {
            //参数对应-> mime type、采样率、声道数
            MediaFormat encodeFormat = MediaFormat.createAudioFormat(encodeType, 44100, 1);
            encodeFormat.setInteger(MediaFormat.KEY_BIT_RATE, 64000);//比特率
            encodeFormat.setInteger(MediaFormat.KEY_CHANNEL_MASK, AudioFormat.CHANNEL_IN_MONO);
            encodeFormat.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC);
            encodeFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, samples_per_frame);//作用于inputBuffer的大小
            mediaEncode = MediaCodec.createEncoderByType(encodeType);
            mediaEncode.configure(encodeFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mediaEncode == null) {
            Log.e(TAG, "create mediaEncode failed");
            return;
        }
        mediaEncode.start();
        encodeInputBuffers = mediaEncode.getInputBuffers();
        encodeOutputBuffers = mediaEncode.getOutputBuffers();
        encodeBufferInfo = new MediaCodec.BufferInfo();
    }


    /**
     * 编码PCM数据 得到AAC格式的音频文件
     */
    private void dstAudioFormatFromPCM(byte[] pcmData) {

        int inputIndex;
        ByteBuffer inputBuffer;
        int outputIndex;
        ByteBuffer outputBuffer;

        int outBitSize;
        int outPacketSize;
        byte[] PCMAudio;
        PCMAudio = pcmData;

        encodeInputBuffers = mediaEncode.getInputBuffers();
        encodeOutputBuffers = mediaEncode.getOutputBuffers();
        encodeBufferInfo = new MediaCodec.BufferInfo();


        inputIndex = mediaEncode.dequeueInputBuffer(0);
        inputBuffer = encodeInputBuffers[inputIndex];
        inputBuffer.clear();
        inputBuffer.limit(PCMAudio.length);
        inputBuffer.put(PCMAudio);//PCM数据填充给inputBuffer
        mediaEncode.queueInputBuffer(inputIndex, 0, PCMAudio.length, 0, 0);//通知编码器 编码


        outputIndex = mediaEncode.dequeueOutputBuffer(encodeBufferInfo, 0);
        while (outputIndex > 0) {

            outBitSize = encodeBufferInfo.size;
            outPacketSize = outBitSize + 7;//7为ADT头部的大小
            outputBuffer = encodeOutputBuffers[outputIndex];//拿到输出Buffer
            outputBuffer.position(encodeBufferInfo.offset);
            outputBuffer.limit(encodeBufferInfo.offset + outBitSize);
            chunkAudio = new byte[outPacketSize];
            addADTStoPacket(chunkAudio, outPacketSize);//添加ADTS
            outputBuffer.get(chunkAudio, 7, outBitSize);//将编码得到的AAC数据 取出到byte[]中

            try {
                //录制aac音频文件，保存在手机内存中
                out.write(chunkAudio, 0, chunkAudio.length);
                out.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            outputBuffer.position(encodeBufferInfo.offset);
            mediaEncode.releaseOutputBuffer(outputIndex, false);
            outputIndex = mediaEncode.dequeueOutputBuffer(encodeBufferInfo, 0);

        }

    }


    /**
     * 添加ADTS头
     *
     * @param packet
     * @param packetLen
     */
    private void addADTStoPacket(byte[] packet, int packetLen) {
        int profile = 2; // AAC LC
        int freqIdx = 8; // 16KHz
        int chanCfg = 1; // CPE

        // fill in ADTS data
        packet[0] = (byte) 0xFF;
        packet[1] = (byte) 0xF1;
        packet[2] = (byte) (((profile - 1) << 6) + (freqIdx << 2) + (chanCfg >> 2));
        packet[3] = (byte) (((chanCfg & 3) << 6) + (packetLen >> 11));
        packet[4] = (byte) ((packetLen & 0x7FF) >> 3);
        packet[5] = (byte) (((packetLen & 7) << 5) + 0x1F);
        packet[6] = (byte) 0xFC;

    }


}
