
package javafxapplication2;

public class Files { 
    
   
    private String FilePath ;
    private String Title ;
    private String Author ;
    private String Tags ;
    private String Summary ;
    private String Comments ;

    public Files( String FilePath, String Title, String Author, String Tags, String Summary, String Comments) {
      
        this.FilePath = FilePath;
        this.Title = Title;
        this.Author = Author;
        this.Tags = Tags;
        this.Summary = Summary;
        this.Comments = Comments;
    }

   

    public void setFilePath(String FilePath) {
        this.FilePath = FilePath;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public void setTags(String Tags) {
        this.Tags = Tags;
    }

    public void setSummary(String Summary) {
        this.Summary = Summary;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }

   

    public String getFilePath() {
        return FilePath;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public String getTags() {
        return Tags;
    }

    public String getSummary() {
        return Summary;
    }

    public String getComments() {
        return Comments;
    }

   
    
    
    
}
