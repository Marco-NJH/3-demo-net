package bank;

import java.sql.SQLException;

public class bankDAO {

	DbHelper db=new DbHelper();
	public void update(String cardno,float money) throws SQLException{
		String sql=" update account set balance=balance + ? where accountid=? ";
		db.update(sql, money,cardno);
	}
	
	public void register(String cardno) throws SQLException{
		String sql=" insert into account values(?,0) ";
		db.update(sql, cardno);
	}
	
	//public void
}
