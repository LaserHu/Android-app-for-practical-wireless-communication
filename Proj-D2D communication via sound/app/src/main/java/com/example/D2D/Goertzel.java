package com.example.D2D;

public class Goertzel {
    static double detection(int sampleNumber,int targetFrequency,int sampleRate,byte[] data){
        int k;
        double omega,cosine,coefficient,q0,q1,q2;
        k=(int)(0.5+((sampleNumber*targetFrequency)/sampleRate));
        omega=(2.0*Math.PI*k)/sampleNumber;
        cosine=Math.cos(omega);
        coefficient=2.0*cosine;
        q1=q2=0;
        for(int i=0;i<sampleNumber;i++){
            q0=coefficient*q1-q2+data[i];
            q2=q1;
            q1=q0;
        }
        return q2*q2+q1*q1-coefficient*q1*q2;
    }
}
