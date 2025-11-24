package models;

public enum Category {
  FOOD("Food"),
  TRANSPORTATION("Transportation"),
  ENTERTAINMENTS("Entertainments"),
  UTILITIES("Utilities"),
  SHOPPING("Shopping"),
  HEALTHCARE("Healthcare"),
  OTHER("Others");
  
  private final String displayName;
  
  Category(String displayName){
    this.displayName = displayName;
  }
  public String getDisplayName(){
    return displayName;

  }
  public static Category findByName(String name){
    if (name == null || name.trim().isEmpty()){
        return OTHER;
    }
    String cleanName = name.trim().toLowerCase();
    for (Category category : Category.values()){
        if(category.displayName.toLowerCase().equals(cleanName)){
            return category;
        }
     }
     return OTHER;
  }
    
}
