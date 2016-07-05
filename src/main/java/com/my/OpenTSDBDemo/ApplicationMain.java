package com.my.OpenTSDBDemo;

import com.my.OpenTSDBDemo.serviceImpl.ServiceImpl;

public class ApplicationMain {
    public static void main( String[] args ) throws Exception{
        ServiceImpl service = new ServiceImpl();
        service.putData();
    }
}
