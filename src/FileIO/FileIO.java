/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileIO;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Student
 */
public class FileIO {

    public static void main(String[] args) {
        String cwd = System.getProperty("user.dir");
        String filename = cwd + "/Sale.txt";
        String filenameRaf = cwd + "/sale.raf";
        String filenameWrite = "mySale.raf";
        ArrayList<Sale> list = new ArrayList<>(1000000);
        ArrayList<Sale> list2 = new ArrayList<>(1000000);
        ArrayList<Sale> list3 = new ArrayList<>(1000000);
        DecimalFormat decf = new DecimalFormat("#,###");
        long startTime = System.nanoTime();
        //read file #1 (time : 6,030,959,700)
        //readDataStream(filename, list);
        //read file #2 (time : 169,451,400) fast than #1
        //readBuffer(filename, list2);
        //read file #3 (time : 1,133,155,399) 
        readRandomAccessFile(filenameRaf, list3);
        //read file #4 (time : 564,900)
        //Sale s = readRandomAccessFileByRecordNumber(filenameRaf, 29044);
        //write file #
        writeRandomAccessFile(filenameWrite, list3);
        long endTime = System.nanoTime();
        System.out.println("Read Data Time :" + decf.format(endTime - startTime));
        System.out.println(list3.get(3).toString());
    }

    public static void writeRandomAccessFile(String fileName, ArrayList<Sale> list) {

        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");

            for (Sale s : list) {
                raf.writeInt(s.getSaleID());
                raf.write(s.getSaleTransactionInBytes());
                raf.write(s.getItemInBytes());
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Sale readRandomAccessFileByRecordNumber(String fileName, int recordNumber) {
        Sale s = new Sale();
        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
            long position = recordNumber + (4 * s.SALETRANSACTION_SIZE + s.ITEM_SIZE);
            raf.seek(position);
            int saleId = raf.readInt();
            byte[] transaction = new byte[s.SALETRANSACTION_SIZE];
            raf.read(transaction);
            byte[] item = new byte[s.ITEM_SIZE];
            raf.read(item);
            s = new Sale(saleId, new String(transaction), new String(item));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public static void readRandomAccessFile(String fileName, ArrayList list) {
        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
            Sale s = new Sale();
            while (raf.getFilePointer() != raf.length()) {
                int saleId = raf.readInt();
                byte[] transaction = new byte[s.SALETRANSACTION_SIZE];
                raf.read(transaction);
                byte[] item = new byte[s.ITEM_SIZE];
                raf.read(item);
                s = new Sale(saleId, new String(transaction), new String(item));
                list.add(s);
            }
            raf.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void readBuffer(String fileName, ArrayList list) {
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(reader);
            String line = "";

            while ((line = buffer.readLine()) != null) {
                processLine(line, list);
            }
            reader.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void readDataStream(String fileName, ArrayList list) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            DataInputStream dataStream = new DataInputStream(fis);
            String line = "";
            while ((line = dataStream.readLine()) != null) {
                processLine(line, list);
            }
            fis.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void processLine(String line, ArrayList list) {
        String[] dataInline = new String[3];
        dataInline = line.split(",'");
        dataInline[0] = dataInline[0].substring(1, dataInline[0].length());
        dataInline[1] = dataInline[1].replace("'", "");
        dataInline[2] = dataInline[2].substring(0, dataInline[2].length() - 3);
        Sale sale = new Sale(Integer.parseInt(dataInline[0]), dataInline[1], dataInline[2]);
        list.add(sale);
    }

}
