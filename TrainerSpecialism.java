import java.io.Serializable;

public class TrainerSpecialism implements Serializable{

    private int trainerID;
    private String specialismName;

    public TrainerSpecialism(int id, String name){
	this.trainerID = id;
	this.specialismName = name;
    }

    public void setID(int id){
	this.trainerID = id;
    }

    public int getID(){
	return this.trainerID;
    }

    public void setName(String name){
	this.specialismName = name;
    }

    public String getName(){
	return this.specialismName;
    }

    @Override
    public String toString(){
	return "TrainerSpecialism [trainerID =" + trainerID + "specialismName" + specialismName+"]";
    }

}
