package com.training.project;

import java.io.IOException;

public class MyController extends Thread {

    MyFileHandler myFileHandler;
    boolean checkReadWrite;

    MyController(String name, MyFileHandler myFileHandler,boolean checkReadWrite)
    {
        super(name);
        this.myFileHandler = myFileHandler;
        this.checkReadWrite = checkReadWrite;
    }
    public void run()
    {

        if(checkReadWrite == true){
            try{
                myFileHandler.read();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try{
                myFileHandler.write();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }



    }

    public static void main(String[] args) throws InterruptedException {


            MyController Thread1 = new MyController("CSVWriteThread", new CSVFileHandler(),true);
            Thread1.start();

            MyController Thread2 = new MyController("XMLWriteThread", new XMLFileHandler() ,true);
            Thread2.start();

            MyController Thread3 = new MyController("JSONWriteThread",new JSONFileHandler(),true);
            Thread3.start();

            Thread1.join();
            Thread2.join();
            Thread3.join();


            MyController Thread4 = new MyController("CSVWriteThread", new CSVFileHandler(),false);
            Thread4.start();

            MyController Thread5 = new MyController("XMLWriteThread",new XMLFileHandler(),false);
            Thread5.start();

            MyController Thread6 = new MyController("JSONWriteThread",new JSONFileHandler(),false);
            Thread6.start();

            Thread4.join();
            Thread5.join();
            Thread6.join();

    }
}
