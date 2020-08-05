package com.example.D2D;


public class SinWave {
    public static byte[] generateSinWave(int freq,int length){
        byte[] data=new byte[length];
        int HEIGHT = 127;
        float increment=(float)(2* Math.PI*freq/44100);
        float angle=0;
        for (int i = 0; i < length; i++) {
            data[i] = (byte) (HEIGHT * (1 - Math.sin(angle)));
            angle+=increment;
            if(angle> Math.PI)
                angle-= Math.PI*2;
        }
        return data;
    }
    public static byte[] makeSinWave3(int freq1,int freq2,int length){
        byte[] data=new byte[length];
        int HEIGHT = 127;
        float increment1=(float)(2*Math.PI*freq1/44100);
        float increment2=(float)(2*Math.PI*freq2/44100);
        float angle1=0;
        float angle2=0;
        for (int i = 0; i < length; i++) {
            data[i] = (byte) (HEIGHT * (1 - (Math.sin(angle1)+Math.sin(angle2))/2));
            angle1+=increment1;
            angle2+=increment2;
            if(angle1>Math.PI)
                angle1-=Math.PI*2;
            if(angle2>Math.PI)
                angle2-=Math.PI*2;
        }
        return data;
    }
}