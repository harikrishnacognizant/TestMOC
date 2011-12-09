package com.mdm.processor.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "MDM_SOURCE_TEMP")
public class MDMSourceTemp implements IModel{
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlias_Name() {
		return Alias_Name;
	}

	public void setAlias_Name(String alias_Name) {
		Alias_Name = alias_Name;
	}

	public String getAlternate_Email() {
		return Alternate_Email;
	}

	public void setAlternate_Email(String alternate_Email) {
		Alternate_Email = alternate_Email;
	}

	public String getBilling_Address_City() {
		return Billing_Address_City;
	}

	public void setBilling_Address_City(String billing_Address_City) {
		Billing_Address_City = billing_Address_City;
	}

	public String getBilling_Address_Country() {
		return Billing_Address_Country;
	}

	public void setBilling_Address_Country(String billing_Address_Country) {
		Billing_Address_Country = billing_Address_Country;
	}

	public String getBilling_Address_Line_1() {
		return Billing_Address_Line_1;
	}

	public void setBilling_Address_Line_1(String billing_Address_Line_1) {
		Billing_Address_Line_1 = billing_Address_Line_1;
	}

	public String getBilling_Address_Line_2() {
		return Billing_Address_Line_2;
	}

	public void setBilling_Address_Line_2(String billing_Address_Line_2) {
		Billing_Address_Line_2 = billing_Address_Line_2;
	}

	public String getBilling_Address_Line_3() {
		return Billing_Address_Line_3;
	}

	public void setBilling_Address_Line_3(String billing_Address_Line_3) {
		Billing_Address_Line_3 = billing_Address_Line_3;
	}

	public String getBilling_Address_Line_4() {
		return Billing_Address_Line_4;
	}

	public void setBilling_Address_Line_4(String billing_Address_Line_4) {
		Billing_Address_Line_4 = billing_Address_Line_4;
	}

	public String getBilling_Address_Line_5() {
		return Billing_Address_Line_5;
	}

	public void setBilling_Address_Line_5(String billing_Address_Line_5) {
		Billing_Address_Line_5 = billing_Address_Line_5;
	}

	public String getBilling_Address_State() {
		return Billing_Address_State;
	}

	public void setBilling_Address_State(String billing_Address_State) {
		Billing_Address_State = billing_Address_State;
	}

	public String getBilling_Address_Zipcode() {
		return Billing_Address_Zipcode;
	}

	public void setBilling_Address_Zipcode(String billing_Address_Zipcode) {
		Billing_Address_Zipcode = billing_Address_Zipcode;
	}

	public String getBirth_Date() {
		return Birth_Date;
	}

	public void setBirth_Date(String birth_Date) {
		Birth_Date = birth_Date;
	}

	public String getCell_Number() {
		return Cell_Number;
	}

	public void setCell_Number(String cell_Number) {
		Cell_Number = cell_Number;
	}

	public String getDriving_License_ID() {
		return Driving_License_ID;
	}

	public void setDriving_License_ID(String driving_License_ID) {
		Driving_License_ID = driving_License_ID;
	}

	public String getEnriched_Flag() {
		return Enriched_Flag;
	}

	public void setEnriched_Flag(String enriched_Flag) {
		Enriched_Flag = enriched_Flag;
	}

	public String getFax_Number() {
		return Fax_Number;
	}

	public void setFax_Number(String fax_Number) {
		Fax_Number = fax_Number;
	}

	public String getFirst_Name() {
		return First_Name;
	}

	public void setFirst_Name(String first_Name) {
		First_Name = first_Name;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getHome_Number() {
		return Home_Number;
	}

	public void setHome_Number(String home_Number) {
		Home_Number = home_Number;
	}

	public String getLast_Name() {
		return Last_Name;
	}

	public void setLast_Name(String last_Name) {
		Last_Name = last_Name;
	}

	public String getMiddle_Name() {
		return Middle_Name;
	}

	public void setMiddle_Name(String middle_Name) {
		Middle_Name = middle_Name;
	}

	public String getNASDAQ_Code() {
		return NASDAQ_Code;
	}

	public void setNASDAQ_Code(String nASDAQ_Code) {
		NASDAQ_Code = nASDAQ_Code;
	}

	public String getNick_Name() {
		return Nick_Name;
	}

	public void setNick_Name(String nick_Name) {
		Nick_Name = nick_Name;
	}

	public String getOffice_Extn_1() {
		return Office_Extn_1;
	}

	public void setOffice_Extn_1(String office_Extn_1) {
		Office_Extn_1 = office_Extn_1;
	}

	public String getOffice_Extn_2() {
		return Office_Extn_2;
	}

	public void setOffice_Extn_2(String office_Extn_2) {
		Office_Extn_2 = office_Extn_2;
	}

	public String getOffice_Number_1() {
		return Office_Number_1;
	}

	public void setOffice_Number_1(String office_Number_1) {
		Office_Number_1 = office_Number_1;
	}

	public String getOffice_Number_2() {
		return Office_Number_2;
	}

	public void setOffice_Number_2(String office_Number_2) {
		Office_Number_2 = office_Number_2;
	}

	public String getOrg_Name() {
		return Org_Name;
	}

	public void setOrg_Name(String org_Name) {
		Org_Name = org_Name;
	}

	public String getParty_Class_Code() {
		return Party_Class_Code;
	}

	public void setParty_Class_Code(String party_Class_Code) {
		Party_Class_Code = party_Class_Code;
	}

	public String getParty_Class_Code_Meaning() {
		return Party_Class_Code_Meaning;
	}

	public void setParty_Class_Code_Meaning(String party_Class_Code_Meaning) {
		Party_Class_Code_Meaning = party_Class_Code_Meaning;
	}

	public String getParty_ID() {
		return Party_ID;
	}

	public void setParty_ID(String party_ID) {
		Party_ID = party_ID;
	}

	public String getParty_Role_Status() {
		return Party_Role_Status;
	}

	public void setParty_Role_Status(String party_Role_Status) {
		Party_Role_Status = party_Role_Status;
	}

	public String getParty_Role_Type() {
		return Party_Role_Type;
	}

	public void setParty_Role_Type(String party_Role_Type) {
		Party_Role_Type = party_Role_Type;
	}

	public String getParty_Role_Type_Meaning() {
		return Party_Role_Type_Meaning;
	}

	public void setParty_Role_Type_Meaning(String party_Role_Type_Meaning) {
		Party_Role_Type_Meaning = party_Role_Type_Meaning;
	}

	public String getPassport_Number() {
		return Passport_Number;
	}

	public void setPassport_Number(String passport_Number) {
		Passport_Number = passport_Number;
	}

	public String getPopular_Name() {
		return Popular_Name;
	}

	public void setPopular_Name(String popular_Name) {
		Popular_Name = popular_Name;
	}

	public String getPrefix() {
		return Prefix;
	}

	public void setPrefix(String prefix) {
		Prefix = prefix;
	}

	public String getPrimary_Address_City() {
		return Primary_Address_City;
	}

	public void setPrimary_Address_City(String primary_Address_City) {
		Primary_Address_City = primary_Address_City;
	}

	public String getPrimary_Address_Country() {
		return Primary_Address_Country;
	}

	public void setPrimary_Address_Country(String primary_Address_Country) {
		Primary_Address_Country = primary_Address_Country;
	}

	public String getPrimary_Address_Line_1() {
		return Primary_Address_Line_1;
	}

	public void setPrimary_Address_Line_1(String primary_Address_Line_1) {
		Primary_Address_Line_1 = primary_Address_Line_1;
	}

	public String getPrimary_Address_Line_2() {
		return Primary_Address_Line_2;
	}

	public void setPrimary_Address_Line_2(String primary_Address_Line_2) {
		Primary_Address_Line_2 = primary_Address_Line_2;
	}

	public String getPrimary_Address_Line_3() {
		return Primary_Address_Line_3;
	}

	public void setPrimary_Address_Line_3(String primary_Address_Line_3) {
		Primary_Address_Line_3 = primary_Address_Line_3;
	}

	public String getPrimary_Address_Line_4() {
		return Primary_Address_Line_4;
	}

	public void setPrimary_Address_Line_4(String primary_Address_Line_4) {
		Primary_Address_Line_4 = primary_Address_Line_4;
	}

	public String getPrimary_Address_Line_5() {
		return Primary_Address_Line_5;
	}

	public void setPrimary_Address_Line_5(String primary_Address_Line_5) {
		Primary_Address_Line_5 = primary_Address_Line_5;
	}

	public String getPrimary_Address_State() {
		return Primary_Address_State;
	}

	public void setPrimary_Address_State(String primary_Address_State) {
		Primary_Address_State = primary_Address_State;
	}

	public String getPrimary_Address_Zipcode() {
		return Primary_Address_Zipcode;
	}

	public void setPrimary_Address_Zipcode(String primary_Address_Zipcode) {
		Primary_Address_Zipcode = primary_Address_Zipcode;
	}

	public String getPrimary_Email() {
		return Primary_Email;
	}

	public void setPrimary_Email(String primary_Email) {
		Primary_Email = primary_Email;
	}

	public String getRegistered_Name() {
		return Registered_Name;
	}

	public void setRegistered_Name(String registered_Name) {
		Registered_Name = registered_Name;
	}

	public String getRegistered_Number() {
		return Registered_Number;
	}

	public void setRegistered_Number(String registered_Number) {
		Registered_Number = registered_Number;
	}

	public String getRep_Address_City() {
		return Rep_Address_City;
	}

	public void setRep_Address_City(String rep_Address_City) {
		Rep_Address_City = rep_Address_City;
	}

	public String getRep_Address_Country() {
		return Rep_Address_Country;
	}

	public void setRep_Address_Country(String rep_Address_Country) {
		Rep_Address_Country = rep_Address_Country;
	}

	public String getRep_Address_Line_1() {
		return Rep_Address_Line_1;
	}

	public void setRep_Address_Line_1(String rep_Address_Line_1) {
		Rep_Address_Line_1 = rep_Address_Line_1;
	}

	public String getRep_Address_Line_2() {
		return Rep_Address_Line_2;
	}

	public void setRep_Address_Line_2(String rep_Address_Line_2) {
		Rep_Address_Line_2 = rep_Address_Line_2;
	}

	public String getRep_Address_Line_3() {
		return Rep_Address_Line_3;
	}

	public void setRep_Address_Line_3(String rep_Address_Line_3) {
		Rep_Address_Line_3 = rep_Address_Line_3;
	}

	public String getRep_Address_Line_4() {
		return Rep_Address_Line_4;
	}

	public void setRep_Address_Line_4(String rep_Address_Line_4) {
		Rep_Address_Line_4 = rep_Address_Line_4;
	}

	public String getRep_Address_Line_5() {
		return Rep_Address_Line_5;
	}

	public void setRep_Address_Line_5(String rep_Address_Line_5) {
		Rep_Address_Line_5 = rep_Address_Line_5;
	}

	public String getRep_Address_State() {
		return Rep_Address_State;
	}

	public void setRep_Address_State(String rep_Address_State) {
		Rep_Address_State = rep_Address_State;
	}

	public String getRep_Address_Zipcode() {
		return Rep_Address_Zipcode;
	}

	public void setRep_Address_Zipcode(String rep_Address_Zipcode) {
		Rep_Address_Zipcode = rep_Address_Zipcode;
	}

	public String getSecondary_Email() {
		return Secondary_Email;
	}

	public void setSecondary_Email(String secondary_Email) {
		Secondary_Email = secondary_Email;
	}

	public String getShipping_Address_City() {
		return Shipping_Address_City;
	}

	public void setShipping_Address_City(String shipping_Address_City) {
		Shipping_Address_City = shipping_Address_City;
	}

	public String getShipping_Address_Country() {
		return Shipping_Address_Country;
	}

	public void setShipping_Address_Country(String shipping_Address_Country) {
		Shipping_Address_Country = shipping_Address_Country;
	}

	public String getShipping_Address_Line_1() {
		return Shipping_Address_Line_1;
	}

	public void setShipping_Address_Line_1(String shipping_Address_Line_1) {
		Shipping_Address_Line_1 = shipping_Address_Line_1;
	}

	public String getShipping_Address_Line_2() {
		return Shipping_Address_Line_2;
	}

	public void setShipping_Address_Line_2(String shipping_Address_Line_2) {
		Shipping_Address_Line_2 = shipping_Address_Line_2;
	}

	public String getShipping_Address_Line_3() {
		return Shipping_Address_Line_3;
	}

	public void setShipping_Address_Line_3(String shipping_Address_Line_3) {
		Shipping_Address_Line_3 = shipping_Address_Line_3;
	}

	public String getShipping_Address_Line_4() {
		return Shipping_Address_Line_4;
	}

	public void setShipping_Address_Line_4(String shipping_Address_Line_4) {
		Shipping_Address_Line_4 = shipping_Address_Line_4;
	}

	public String getShipping_Address_Line_5() {
		return Shipping_Address_Line_5;
	}

	public void setShipping_Address_Line_5(String shipping_Address_Line_5) {
		Shipping_Address_Line_5 = shipping_Address_Line_5;
	}

	public String getShipping_Address_State() {
		return Shipping_Address_State;
	}

	public void setShipping_Address_State(String shipping_Address_State) {
		Shipping_Address_State = shipping_Address_State;
	}

	public String getShipping_Address_Zipcode() {
		return Shipping_Address_Zipcode;
	}

	public void setShipping_Address_Zipcode(String shipping_Address_Zipcode) {
		Shipping_Address_Zipcode = shipping_Address_Zipcode;
	}

	public String getSouce_System_Code() {
		return Souce_System_Code;
	}

	public void setSouce_System_Code(String souce_System_Code) {
		Souce_System_Code = souce_System_Code;
	}

	public String getSource_Identifier() {
		return Source_Identifier;
	}

	public void setSource_Identifier(String source_Identifier) {
		Source_Identifier = source_Identifier;
	}

	public String getSource_System_Description() {
		return Source_System_Description;
	}

	public void setSource_System_Description(String source_System_Description) {
		Source_System_Description = source_System_Description;
	}

	public String getSource_System_Meaning() {
		return Source_System_Meaning;
	}

	public void setSource_System_Meaning(String source_System_Meaning) {
		Source_System_Meaning = source_System_Meaning;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
	}

	public String getState_License_ID() {
		return State_License_ID;
	}

	public void setState_License_ID(String state_License_ID) {
		State_License_ID = state_License_ID;
	}

	public String getSuffix() {
		return Suffix;
	}

	public void setSuffix(String suffix) {
		Suffix = suffix;
	}

	public String getTax_ID() {
		return Tax_ID;
	}

	public void setTax_ID(String tax_ID) {
		Tax_ID = tax_ID;
	}

	public String getTo_Be_Enriched_Flag() {
		return To_Be_Enriched_Flag;
	}

	public void setTo_Be_Enriched_Flag(String to_Be_Enriched_Flag) {
		To_Be_Enriched_Flag = to_Be_Enriched_Flag;
	}

	public String getTrade_Mark_ID() {
		return Trade_Mark_ID;
	}

	public void setTrade_Mark_ID(String trade_Mark_ID) {
		Trade_Mark_ID = trade_Mark_ID;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	private String Alias_Name;
	private String Alternate_Email;
	private String Billing_Address_City;
	private String Billing_Address_Country;
	private String Billing_Address_Line_1;
	private String Billing_Address_Line_2;
	private String Billing_Address_Line_3;
	private String Billing_Address_Line_4;
	private String Billing_Address_Line_5;
	private String Billing_Address_State;
	private String Billing_Address_Zipcode;
	private String Birth_Date;
	private String Cell_Number;
	private String Driving_License_ID;
	private String Enriched_Flag;
	private String Fax_Number;
	private String First_Name;
	private String Gender;
	private String Home_Number;
	private String Last_Name;
	private String Middle_Name;
	private String NASDAQ_Code;
	private String Nick_Name;
	private String Office_Extn_1;
	private String Office_Extn_2;
	private String Office_Number_1;
	private String Office_Number_2;
	private String Org_Name;
	private String Party_Class_Code;
	private String Party_Class_Code_Meaning;
	private String Party_ID;
	private String Party_Role_Status;
	private String Party_Role_Type;
	private String Party_Role_Type_Meaning;
	private String Passport_Number;
	private String Popular_Name;
	private String Prefix;
	private String Primary_Address_City;
	private String Primary_Address_Country;
	private String Primary_Address_Line_1;
	private String Primary_Address_Line_2;
	private String Primary_Address_Line_3;
	private String Primary_Address_Line_4;
	private String Primary_Address_Line_5;
	private String Primary_Address_State;
	private String Primary_Address_Zipcode;
	private String Primary_Email;
	private String Registered_Name;
	private String Registered_Number;
	private String Rep_Address_City;
	private String Rep_Address_Country;
	private String Rep_Address_Line_1;
	private String Rep_Address_Line_2;
	private String Rep_Address_Line_3;
	private String Rep_Address_Line_4;
	private String Rep_Address_Line_5;
	private String Rep_Address_State;
	private String Rep_Address_Zipcode;
	private String Secondary_Email;
	private String Shipping_Address_City;
	private String Shipping_Address_Country;
	private String Shipping_Address_Line_1;
	private String Shipping_Address_Line_2;
	private String Shipping_Address_Line_3;
	private String Shipping_Address_Line_4;
	private String Shipping_Address_Line_5;
	private String Shipping_Address_State;
	private String Shipping_Address_Zipcode;
	private String Souce_System_Code;
	private String Source_Identifier;
	private String Source_System_Description;
	private String Source_System_Meaning;
	private String SSN;
	private String State_License_ID;
	private String Suffix;
	private String Tax_ID;
	private String To_Be_Enriched_Flag;
	private String Trade_Mark_ID;
	private String URL;

}
