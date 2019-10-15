/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileIO;

/**
 *
 * @author Student
 */
public class Sale {

    @Override
    public String toString() {
        return "Sale{" + "saleID=" + saleID + ", saleTransaction=" + saleTransaction + ", item=" + item + ", SALETRANSACTION_SIZE=" + SALETRANSACTION_SIZE + ", ITEM_SIZE=" + ITEM_SIZE + '}';
    }

    private int saleID;
    private String saleTransaction;
    private String item;
    final int SALETRANSACTION_SIZE = 20;
    final int ITEM_SIZE = 50;

    public Sale(int saleID, String saleTransaction, String item) {
        this.saleID = saleID;
        this.saleTransaction = saleTransaction;
        this.item = item;
    }

    Sale() {
    }

    public String getItem() {
        return item;
    }

    public byte[] getItemInBytes() {
        byte[] itemBytes = new byte[ITEM_SIZE];
        System.arraycopy(item.getBytes(), 0, itemBytes, 0, item.length());
        return itemBytes;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getSaleID() {
        return saleID;
    }

    public void setSaleID(int saleID) {
        this.saleID = saleID;
    }

    public String getSaleTransaction() {
        return saleTransaction;
    }

    public byte[] getSaleTransactionInBytes() {
        byte[] transactionBytes = new byte[SALETRANSACTION_SIZE];
        System.arraycopy(saleTransaction.getBytes(), 0, transactionBytes, 0, saleTransaction.length());
        return transactionBytes;
    }

    public void setSaleTransaction(String saleTransaction) {
        this.saleTransaction = saleTransaction;
    }

}
