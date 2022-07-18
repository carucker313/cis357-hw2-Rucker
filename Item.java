/**The Item object being used, has the attributes, itemCode, itemName, and unitPrice**/
public class Item {
    String itemCode;
    String itemName;
    double unitPrice;
    public Item(String newCode,String newName, double newPrice){
        itemCode = newCode;
        itemName = newName;
        unitPrice = newPrice;
    }
}
