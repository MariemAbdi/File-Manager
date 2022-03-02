
package javafxapplication2;

public class util_tag {
    String Tag_Id;
    Integer number;
    public util_tag(String Tag_Id,Integer number) {
        this.Tag_Id = Tag_Id;
    this.number=number;}

    public util_tag(String Tag_Id) {
         this.Tag_Id = Tag_Id;}

    public String getTag_Id() {
        return Tag_Id;
    }

    public void setTag_Id(String Tag_Id) {
        this.Tag_Id = Tag_Id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    
  
    

    
}
