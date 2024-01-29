public class City {
    private String title;

    public City(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
    public String toString(){return title;}
    @Override
    public boolean equals(Object obj){
        if(this==obj) return true;
        if(!(obj instanceof City)) return false;
        return this.title.equals( ((City) obj).title );
    }
}
