
public class Data {
	private Integer confirmed96, confirmed01, confirmed06, confirmed11, stateCode, countyCode;
	private String stateName, countyName;

	public Data() {
		confirmed96 = 0;
		confirmed01 = 0;
		confirmed06 = 0;
		confirmed11 = 0;
		stateCode = 0;
		countyCode = 0;
		stateName = "";
		countyName = "";
	}

	public Data(String cName, String sName, int cCode, int stateCode, int confirmed96, int confirmed01, int confirmed06,
			int confirmed11) {
		this.countyName = cName;
		this.stateName = sName;
		this.stateCode = stateCode;
		this.countyCode = cCode;
		this.confirmed96 = confirmed96;
		this.confirmed01 = confirmed01;
		this.confirmed06 = confirmed06;
		this.confirmed11 = confirmed11;
		
		
	}
	public void printData() {
		System.out.printf("%s  ",String.valueOf(stateCode) );
		System.out.printf("%s  ",String.valueOf(countyCode) );
		System.out.printf("%s     ",stateName );
		System.out.printf("%s   ",countyName );
		System.out.printf("%s  ",String.valueOf(confirmed96) );
		System.out.printf("%s  ",String.valueOf(confirmed01) );
		System.out.printf("%s  ",String.valueOf(confirmed06) );
		System.out.printf("%s  ",String.valueOf(confirmed11) );
		System.out.println();
		
	}


	public int getConfirmed96() {
		return confirmed96;
	}

	public void setConfirmed96(int confirmed96) {
		this.confirmed96 = confirmed96;
	}

	public int getConfirmed01() {
		return confirmed01;
	}

	public void setConfirmed01(int confirmed01) {
		this.confirmed01 = confirmed01;
	}

	public int getConfirmed06() {
		return confirmed06;
	}

	public void setConfirmed06(int confirmed06) {
		this.confirmed06 = confirmed06;
	}

	public int getConfirmed11() {
		return confirmed11;
	}

	public void setConfirmed11(int confirmed11) {
		this.confirmed11 = confirmed11;
	}

	public int getStateCode() {
		return stateCode;
	}

	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}

	public int getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(int countyCode) {
		this.countyCode = countyCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

}
