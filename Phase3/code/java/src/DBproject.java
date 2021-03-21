/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */

public class DBproject{
	//reference to physical database connection
	private Connection _connection = null;
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static boolean valid_ID(String str, int strnum) {
		if (str.trim().equals("")) {
			System.out.println("empty");
		    return false;
		} else {
			str = str.trim();
			int temp;
			try {
				temp = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				System.out.println(str + " is not a valid number");
				return false;
			}
			if ((temp >= 0) && (temp <= strnum)) {
				System.out.println(str + " is valid");
				return true;
			} else {
				System.out.println(str + " is not within range");
				return false;
			}
		}
	}
	public static boolean checkpos(String str) {
		if (str.trim().equals("")) {
			System.out.println("no input");
		    return false;
		} else {
		    int temp;
			try {
				temp = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				System.out.println(str + " is not a valid number");
				return false;
			}
			if (temp >= 0) {
				System.out.println(str + " is positive");
				return true;
			} else {
				System.out.println(str + " is negative");
				return false;
			}
		}
	}
	
	public static boolean checkposdble(String str) {
		if (str.trim().equals("")) {
			System.out.println("no input");
		    return false;
		} else {
		    double temp;
			try {
				temp = Double.parseDouble(str);
			} catch (NumberFormatException e) {
				System.out.println(str + " is not a double");
				return false;
			}
			if (temp >= 0) {
				System.out.println(str + " is a valid double");
				return true;
			} else {
				System.out.println(str + " is negative");
				return false;
			}
		}
	}

	public static boolean valid_RWC(String str) {
		str = str.trim();
		if (str.length() == 1) {
			if ((str == "R") || (str == "W") || (str == "C")) {
				System.out.println(str + " is valid ('R','W',or 'C')");
				return true;
			}
		} 
		System.out.println(str + " is not ('R','W',or 'C')");
		return false;
	}

	public static boolean valid_date(String str) {
		if (str.trim().equals("")) {
			System.out.println("empty");
		    return false;
		} else {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    sdf.setLenient(false);
		    try {
		        Date date = sdf.parse(str); 
		        System.out.println(str + " is a valid Date format");
		    } catch (ParseException e) {
		        System.out.println(str + " is an invalid Date format");
		        return false;
		    }
		    return true;
		}
	}

	public static boolean check_r(String str) {
		if (str.trim().equals("")) {
			System.out.println("empty");
		    return false;
		} else {
		    int temp;
			try {
				temp = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				System.out.println(str + " is not a number");
				return false;
			}
			if ((temp >= 0) && (temp <= 500)) {
				System.out.println(str + " is valid 0-500");
				return true;
			} else {
				System.out.println(str + " is not in 0-500");
				return false;
			}
		}
	}

	
	public static boolean valid_char5(String str) {
		str = str.trim();
		if (str.length() == 5) {
			System.out.println(str + " is 5 chars");
			return true;
		} else {
			System.out.println(str + " is not 5 chars");
			return false;
		}
	}
	public static boolean valid_char24(String str) {
		str = str.trim();
		if (str.length() <= 24) {
			System.out.println(str + " is less than 25 chars");
			return true;
		} else {
			System.out.println(str + " is over 24 chars");
			return false;
		}
	}
	
	public static boolean valid_char32(String str) {
		str = str.trim();
		if (str.length() <= 32) {
			System.out.println(str + " is less than 33 chars");
			return true;
		} else {
			System.out.println(str + " is more than 32 chars");
			return false;
		}
	}

	
	public static boolean valid_char64(String str) {
		str = str.trim();
		if (str.length() <= 64) {
			System.out.println(str + " is less than 65 characters");
			return true;
		} else {
			System.out.println(str + " is more than 64 characters");
			return false;
		}
	}

	
	public static boolean valid_char128(String str) {
		str = str.trim();
		if (str.length() <= 128) {
			System.out.println(str + " is less than 129 characters");
			return true;
		} else {
			System.out.println(str + " is more than 128 characters");
			return false;
		}
	}


	
	public DBproject(String dbname, String dbport, String user, String passwd) throws SQLException {
		System.out.print("Connecting to database...");
		try{
			// constructs the connection URL
			String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
			System.out.println ("Connection URL: " + url + "\n");
			
			// obtain a physical connection
	        this._connection = DriverManager.getConnection(url, user, passwd);
	        System.out.println("Done");
		}catch(Exception e){
			System.err.println("Error - Unable to Connect to Database: " + e.getMessage());
	        System.out.println("Make sure you started postgres on this machine");
	        System.exit(-1);
		}
	}
	
	/**
	 * Method to execute an update SQL statement.  Update SQL instructions
	 * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
	 * 
	 * @param sql the input SQL string
	 * @throws java.sql.SQLException when update failed
	 * */
	public void executeUpdate (String sql) throws SQLException { 
		// creates a statement object
		Statement stmt = this._connection.createStatement ();

		// issues the update instruction
		stmt.executeUpdate (sql);

		// close the instruction
	    stmt.close ();
	}//end executeUpdate

	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and outputs the results to
	 * standard out.
	 * 
	 * @param query the input query string
	 * @return the number of rows returned
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public int executeQueryAndPrintResult (String query) throws SQLException {
		//creates a statement object
		Statement stmt = this._connection.createStatement ();

		//issues the query instruction
		ResultSet rs = stmt.executeQuery (query);

		/*
		 *  obtains the metadata object for the returned res set.  The metadata
		 *  contains row and column info.
		 */
		ResultSetMetaData rsmd = rs.getMetaData ();
		int numCol = rsmd.getColumnCount ();
		int rowCount = 0;
		
		//iterates through the res set and output them to standard out.
		boolean outputHeader = true;
		while (rs.next()){
			if(outputHeader){
				for(int i = 1; i <= numCol; i++){
					System.out.print(rsmd.getColumnName(i) + "\t");
			    }
			    System.out.println();
			    outputHeader = false;
			}
			for (int i=1; i<=numCol; ++i)
				System.out.print (rs.getString (i) + "\t");
			System.out.println ();
			++rowCount;
		}//end while
		stmt.close ();
		return rowCount;
	}
	
	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and returns the results as
	 * a list of records. Each record in turn is a list of attribute values
	 * 
	 * @param query the input query string
	 * @return the query res as a list of records
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public List<List<String>> executeQueryAndReturnResult (String query) throws SQLException { 
		//creates a statement object 
		Statement stmt = this._connection.createStatement (); 
		
		//issues the query instruction 
		ResultSet rs = stmt.executeQuery (query); 
	 
		/*
		 * obtains the metadata object for the returned res set.  The metadata 
		 * contains row and column info. 
		*/ 
		ResultSetMetaData rsmd = rs.getMetaData (); 
		int numCol = rsmd.getColumnCount (); 
		int rowCount = 0; 
	 
		//iterates through the res set and saves the data returned by the query. 
		boolean outputHeader = false;
		List<List<String>> res  = new ArrayList<List<String>>(); 
		while (rs.next()){
			List<String> record = new ArrayList<String>(); 
			for (int i=1; i<=numCol; ++i) 
				record.add(rs.getString (i)); 
			res.add(record); 
		}//end while 
		stmt.close (); 
		return res; 
	}//end executeQueryAndReturnResult
	
	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and returns the number of results
	 * 
	 * @param query the input query string
	 * @return the number of rows returned
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public int executeQuery (String query) throws SQLException {
		//creates a statement object
		Statement stmt = this._connection.createStatement ();

		//issues the query instruction
		ResultSet rs = stmt.executeQuery (query);

		int rowCount = 0;

		//iterates through the res set and count nuber of results.
		if(rs.next()){
			rowCount++;
		}//end while
		stmt.close ();
		return rowCount;
	}
	
	/**
	 * Method to fetch the last value from sequence. This
	 * method issues the query to the DBMS and returns the current 
	 * value of sequence used for autogenerated keys
	 * 
	 * @param sequence name of the DB sequence
	 * @return current value of a sequence
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	
	public int getCurrSeqVal(String sequence) throws SQLException {
		Statement stmt = this._connection.createStatement ();
		
		ResultSet rs = stmt.executeQuery (String.format("Select currval('%s')", sequence));
		if (rs.next()) return rs.getInt(1);
		return -1;
	}

	/**
	 * Method to close the physical connection if it is open.
	 */
	public void cleanup(){
		try{
			if (this._connection != null){
				this._connection.close ();
			}//end if
		}catch (SQLException e){
	         // ignored.
		}//end try
	}//end cleanup

	/**
	 * The main execution method
	 * 
	 * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
	 */
	public static void main (String[] args) {
		if (args.length != 3) {
			System.err.println (
				"Usage: " + "java [-classpath <classpath>] " + DBproject.class.getName () +
		            " <dbname> <port> <user>");
			return;
		}//end if
		
		DBproject esql = null;
		
		try{
			System.out.println("(1)");
			
			try {
				Class.forName("org.postgresql.Driver");
			}catch(Exception e){

				System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
				e.printStackTrace();
				return;
			}
			
			System.out.println("(2)");
			String dbname = args[0];
			String dbport = args[1];
			String user = args[2];
			
			esql = new DBproject (dbname, dbport, user, "");
			
			boolean keepon = true;
			while(keepon){
				System.out.println("MAIN MENU");
				System.out.println("---------");
				System.out.println("1. Add Ship");
				System.out.println("2. Add Captain");
				System.out.println("3. Add Cruise");
				System.out.println("4. Book Cruise");
				System.out.println("5. List number of available seats for a given Cruise.");
				System.out.println("6. List total number of repairs per Ship in descending order");
				System.out.println("7. Find total number of passengers with a given stat");
				System.out.println("8. < EXIT");
				
				switch (readChoice()){
					case 1: AddShip(esql); break;
					case 2: AddCaptain(esql); break;
					case 3: AddCruise(esql); break;
					case 4: BookCruise(esql); break;
					case 5: ListNumberOfAvailableSeats(esql); break;
					case 6: ListsTotalNumberOfRepairsPerShip(esql); break;
					case 7: FindPassengersCountWithStatus(esql); break;
					case 8: keepon = false; break;
				}
			}
		}catch(Exception e){
			System.err.println (e.getMessage ());
		}finally{
			try{
				if(esql != null) {
					System.out.print("Disconnecting from database...");
					esql.cleanup ();
					System.out.println("Done\n\nBye !");
				}//end if				
			}catch(Exception e){
				// ignored.
			}
		}
	}

	public static int readChoice() {
		int input;
		// returns only if a correct value is given.
		do {
			System.out.print("Please make your choice: ");
			try { // read the integer, parse it and break.
				input = Integer.parseInt(in.readLine());
				break;
			}catch (Exception e) {
				System.out.println("Your input is invalid!");
				continue;
			}//end try
		}while (true);
		return input;
	}//end readChoice

	public static void AddShip(DBproject esql) {//1
		do{
			try{
       	 		
                System.out.print("Enter new Ship's make: ");
                String ship_make = in.readLine();
                while (!valid_char32(ship_make)) {
                	ship_make = in.readLine();
                }

				System.out.print("Enter new Ship's model: ");
                String ship_model = in.readLine();
                while (!valid_char64(ship_model)) {
                	ship_model = in.readLine();
                }
	
				System.out.print("Enter new Ship's age: ");
                String ship_age = in.readLine();
                while (!checkpos(ship_age)) {
                	ship_age = in.readLine();
                }
				
				System.out.print("Enter new Ship's # of available seats (between 0 and 500): ");
                String free_seats = in.readLine();
                while (!check_r(free_seats)) {
                	free_seats = in.readLine();
                }
				
				
				String sid_q = "SELECT MAX(S.id) FROM Ship S;";
				String res = esql.executeQueryAndReturnResult(sid_q).get(0).get(0);
				String sid = String.valueOf(Integer.parseInt(res)+1);
				
				String query = "INSERT INTO Ship (id, make, model, age, seats) VALUES (";
				
				query += sid + ", ";
				query += "'" + ship_make + "', ";
				query += "'" +ship_model + "', ";
				query += ship_age + ", ";
				query += free_seats + ");";
				
				
				esql.executeUpdate(query);
				
         		System.out.println ("\nAdded Ship  " + sid + " to the database.\n");
				
				break;
      		}catch(Exception e){
         		
				e.printStackTrace();
				continue;
      		}
      	}while (true);
	}

	public static void AddCaptain(DBproject esql) {//2
		do{
			try{
				
				System.out.print("Enter new Captain's full name: ");
				String capt_name = in.readLine();
				while (!valid_char128(capt_name)) {
                	capt_name = in.readLine();
                }
				System.out.print("Enter new Captain's nationality: ");
				String capt_nat = in.readLine();
				while (!valid_char24(capt_nat)) {
                	capt_nat = in.readLine();
                }


				
				String cid_q = "SELECT MAX(C.id) FROM Captain C";
				String res = esql.executeQueryAndReturnResult(cid_q).get(0).get(0);
				String cid = String.valueOf(Integer.parseInt(res)+1);
				

				String query = "INSERT INTO Captain (id, fullname, nationality) VALUES (";
				
				query += cid + ", ";
				query += "'" + capt_name + "', ";
				query += "'" +capt_nat + "'); ";

				
				esql.executeUpdate(query);
				
				System.out.println ("\nAdded Captain " + cid + " to the database.\n");

				break;
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}while (true);
	}

	public static void AddCruise(DBproject esql) {//3
        do{
            try{
				
		        System.out.print("Enter new Cruise's ticket price: ");
		        String tix_price = in.readLine();
		        while (!checkposdble(tix_price)) {
                	tix_price = in.readLine();
                }
		        System.out.print("Enter new Cruise's number of tickets sold already: ");
		        String tix_sold = in.readLine();
		        while (!checkpos(tix_sold)) {
                	tix_sold = in.readLine();
                }
			System.out.print("Enter new Cruise's number of stops: ");
		        String stop_num = in.readLine();
		        while (!checkpos(stop_num)) {
                	stop_num = in.readLine();
                }
			System.out.print("Enter new Cruise's departure date (YYYY-MM-DD): ");
		        String date_of_dep = in.readLine();
		        while (!valid_date(date_of_dep)) {
                	date_of_dep = in.readLine();
                }
			System.out.print("Enter new Cruise's arrival date(YYYY-MM-DD): ");
		        String date_of_arr = in.readLine();
		        while (!valid_date(date_of_arr)) {
                	date_of_arr = in.readLine();
                }
			System.out.print("Enter new Cruise's departure port code (5 chars): ");
		        String dep_p_code = in.readLine();
		        while (!valid_char5(dep_p_code)) {
                	dep_p_code = in.readLine();
                }
			System.out.print("Enter new Cruise's arrival port code (5 chars): ");
		        String arr_p_code = in.readLine();
		        while (!valid_char5(arr_p_code)) {
                	arr_p_code = in.readLine();
                }

				
		        String q_cnum = "SELECT MAX(C.cnum) FROM Cruise C;";
		        String res = esql.executeQueryAndReturnResult(q_cnum).get(0).get(0);
		        String c_num = String.valueOf(Integer.parseInt(res)+1);
				

				
		        String query = "INSERT INTO Cruise VALUES (";
				
		        query += c_num + ", ";
				query += tix_price + ", ";
				query += tix_sold + ", ";
				query += stop_num + ", ";
				
				query += "'" + date_of_dep + "', ";
				query += "'" + date_of_arr + "', ";
		        query += "'" + arr_p_code + "', ";
		        query += "'" +dep_p_code + "'); ";
				
		        esql.executeUpdate(query);
				
		        System.out.println ("\nSuccessfully added Cruise " + c_num + " to the database.\n");

		        break;
	        }catch(Exception e){
                e.printStackTrace();
                continue;
	        }
    	}while (true);
	
	}


	public static void BookCruise(DBproject esql) {//4
		
		do{
            try{
				
	        String strnum_q = "SELECT MAX(C.id) FROM Customer C;";
	        String res_strnum = esql.executeQueryAndReturnResult(strnum_q).get(0).get(0);
		int strnum = Integer.parseInt(res_strnum);

				
				
  		System.out.print("Enter you Customer ID: ");
                String c_id = in.readLine();
                while (!valid_ID(c_id,strnum)) {
                	c_id = in.readLine();
                }
                System.out.print("Enter Cruise number you wish to book: ");
                String cruise_num = in.readLine();
                while (!checkpos(cruise_num)) {
                	cruise_num = in.readLine();
                }
				
				
				String reserve_q = "SELECT MAX(R.rnum) FROM Reservation R;"; 
				String res = esql.executeQueryAndReturnResult(reserve_q).get(0).get(0);
				String rnum = String.valueOf(Integer.parseInt(res)+1);

				
				String stat_q = "SELECT  S.seats, C.num_sold FROM Cruise C, Ship S, CruiseInfo CI WHERE CI.cruise_id = "+ cruise_num +" AND CI.ship_id = S.id;";
				List<List<String>> result_status = esql.executeQueryAndReturnResult(stat_q);
				
				
				String stat = "C";
				if (Integer.parseInt(result_status.get(0).get(0))  > Integer.parseInt(result_status.get(0).get(1))) {
					stat = "R";
				} else {
					stat = "W";
				}
				
				
				String tix_stat = "UPDATE Cruise SET num_sold = num_sold+1 WHERE cnum = "+cruise_num+";";
				esql.executeUpdate(tix_stat);

				
				if (stat == "R") {
					System.out.println("\nSuccessfully Reserved a ticket for Cruise "+cruise_num+" for Customer "+c_id+".\n");
				} else {
					System.out.println("\nSuccessfully Waitlisted a ticket for Cruise "+cruise_num+" for Customer "+c_id+".\n");
 				}
					


				
				String query = "INSERT INTO  Reservation (rnum, ccid, cid, status) VALUES (";
                query += rnum + ", ";
				query += c_id + ", ";
				query += cruise_num + ", ";
                query += "'" + stat + "');";
 
				esql.executeUpdate(query);
				
				System.out.println ("\nSuccessfully added Reservation("+stat+") with id " + rnum + " to the database.\n");
	
				

            	break;
          	}catch(Exception e){
                e.printStackTrace();
            	continue;
    		}
        }while (true);

	}

	public static void ListNumberOfAvailableSeats(DBproject esql) {//5
		
		do{
            try{
                
		System.out.print("Enter Cruise number: ");
                String c_num = in.readLine();
                while (!checkpos(c_num)) {
                	c_num = in.readLine();
                }
                System.out.print("Enter Cruise Departure date (yyyy-MM-dd): ");
                String dep_date = in.readLine();
                while (!valid_date(dep_date)) {
                	dep_date = in.readLine();
                }
				
				
				String seats_q = "SELECT C.num_sold, S.seats FROM Cruise C, Ship S, CruiseInfo CI WHERE CI.cruise_id = "+c_num+" AND CI.cruise_id = C.cnum AND C.actual_departure_date = '"+dep_date+"' AND CI.ship_id = S.id;";
                                
				
				List<List<String>> res_seats = esql.executeQueryAndReturnResult(seats_q);
				
				
				int avail_seats = 501; 
				if (res_seats.size() > 0) {
					avail_seats = Integer.parseInt(res_seats.get(0).get(1)) -  Integer.parseInt(res_seats.get(0).get(0));
				}
				
				if (avail_seats == 501) {System.out.println("\nNo Cruises on that date.\n");}
				else if (avail_seats <= 0) {System.out.println("\nNo seats left for this cruise.\n");}
				else {System.out.println("\nThere are "+ String.valueOf(avail_seats) +" seats available for this cruise.\n");}

                break;
            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }while (true);
	
	}

	public static void ListsTotalNumberOfRepairsPerShip(DBproject esql) {//6
		
		try{
			
			String query = "SELECT S.id as Ship_ID, COUNT(R.rid) as Repairs_Made FROM Ship S, Repairs R WHERE S.id = R.ship_id GROUP BY S.id ORDER BY COUNT(R.rid) DESC;";
			
			System.out.print("\n");
			esql.executeQueryAndPrintResult(query);
			System.out.print("\n");
		}catch(Exception e) {
			e.printStackTrace();}

	}

	
	public static void FindPassengersCountWithStatus(DBproject esql) {//7
		
		do{
			try{
				
				System.out.print("Enter Cruise number: ");
                String c_num = in.readLine();
                while (!checkpos(c_num)) {
                	c_num = in.readLine();
                }
                System.out.print("Enter Reservation status ('R','W',or 'C'): ");
                String stat = in.readLine();
                while (!valid_RWC(stat)) {
                	stat = in.readLine();
                }
				
				
				String query = "SELECT R.status, COUNT(DISTINCT R.ccid) FROM Reservation R WHERE cid = "+c_num+" AND stat = '"+stat+"' GROUP BY R.status;";
				
				
				System.out.print("\n");
				esql.executeQueryAndPrintResult(query);
				System.out.print("\n");
				break;
			}catch(Exception e) {
				e.printStackTrace();
				continue;
			}
		}while(true);
	}
}
