package br.ufms.facom;

public enum Sections
{
	MOVIES(0), SECTION_1(1), SECTION_2(2);
	
	private int code;
	
	Sections(int code)
	{
		this.code = code;
	}
	
	int code()
	{
		return code;
	}
	
	static Sections toSection(int code)
	{
		for (Sections sec : values())
		{
			if (sec.code == code)
				return sec;
		}
		
		return null;
	}
}
