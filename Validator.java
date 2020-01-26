import java.time.*;
import java.util.*;
import java.text.*;
import java.time.format.*;

class Validator{

        public boolean checkDateFormat(String date){
	    
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	sdf.setLenient(false);

	try{
	    //Format is correct
	    sdf.parse(date);
	    return true;
	}catch(ParseException e){
	    //Format is incorrect
	    return false;
	}
    }

    public boolean checkTimeFormat(String time){
	
	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm")
	    .withResolverStyle(ResolverStyle.STRICT);

	try{
	    //Format is correct
	    LocalTime.parse(time, timeFormat);
	    return true;
	}catch(DateTimeParseException | NullPointerException e){
	    //Format is incorrect
	    return false;
	}
    }

    public boolean validateDate(String bookingDate){
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	sdf.setLenient(false);

	Date   todaysDate = new Date();
	String currentDate;
	int    currentYear, currentMonth, currentDay;
	int    bookingYear, bookingMonth, bookingDay;
	
	currentDate = sdf.format(todaysDate);
	currentYear = Integer.parseInt(currentDate.substring(6));
	currentMonth = Integer.parseInt(currentDate.substring(3 ,5));
	currentDay = Integer.parseInt(currentDate.substring(0 ,2));
	bookingYear = Integer.parseInt(bookingDate.substring(6));
	bookingMonth = Integer.parseInt(bookingDate.substring(3, 5));
	bookingDay = Integer.parseInt(bookingDate.substring(0, 2));

	//Scenario 1: Booking year and month are the same as current
	if(bookingYear == currentYear && bookingMonth == currentMonth){
	    if(bookingDay > currentDay){
		return true;
	    }
	    else{
		return false;
	    }
	}

	//Scenario 2: Booking year is greater than current (should be less than 2 years realistically)
	else if(bookingYear > currentYear && bookingYear < (currentYear + 2)){
		return true;
	    }

	//Scenario 3: Booking year is the same but booking month is not
	else if(bookingYear == currentYear && bookingMonth > currentMonth){
		return true;
	    }

	//Scenario 4: Booking year is less than current year which means it is invalid
	else{
	    return false;
	}
    }

    public boolean checkDoubleBooking(Booking booking){
	List<Booking> allBookings = new ArrayList<Booking>();
	ApplicationProtocol appProtocol = new ApplicationProtocol();
	boolean doubleBooking = false;

	allBookings = appProtocol.listAllBookings();
	System.out.println(allBookings.size());
	
	for(Booking b : allBookings){
	    if(b.getStartTime().equals(booking.getStartTime()) && b.getDate().equals(booking.getDate())){
		System.out.println("Zobbi kbir");
		if(b.getPTID() == booking.getPTID()){
		    System.out.println("This PT is already booked for this time and date");
		    doubleBooking = true;
		}
		else if(b.getClientID() == booking.getClientID()){		
		    System.out.println("This client is already booked for this time and date");
		    doubleBooking = true;
		}
	    }
	}
	System.out.println("Zobbi zghir");
	return doubleBooking;
    }
}
