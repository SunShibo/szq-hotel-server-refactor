package com.szq.hotel.util;

import java.util.ArrayList;
import java.util.Arrays;

public class OtherAll {
	public static final int ID=0;
	public static final int NAME=0;
	public static final int ID_NUMBER=1;
	public static final int PHONE=2;
	public static final int REAMK=3;
	public static final int GENDER=4;
	
	private ArrayList<Entity>  list=new ArrayList<Entity>();

	private OtherAll(){
	}
	public OtherAll(String message){

		if(message==null){
			return;
		}
		//"1-姓名-证件号码-手机号码-备注- ,2-姓名-证件号码-手机号-备注-,";
		String[]  messs=message.split(",");
		for(int i=0;i<messs.length;i++) {
			String[] mes=messs[i].split("-");
			

				Entity  ent=new Entity();
				ent.setId(Integer.parseInt(mes[ID].trim()));
				String []  m=new String[5];
				m[NAME]=mes[NAME+1];
				m[ID_NUMBER]=mes[ID_NUMBER+1];
				m[PHONE]=mes[PHONE+1];
				m[REAMK]=mes[REAMK+1];
				m[GENDER]=mes[GENDER+1].trim();
				ent.setMessage(m);
				list.add(ent);

		}
		
	}
	
	public Entity getEntity(int  roomId) {
		Entity  ent = null;
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getId()==roomId) {
				ent = list.get(i);
				list.remove(ent);
				break;
			}
		}
		return ent;
	}
	
	
	
	public class  Entity{
		private int id;
		private String[]  message;
		
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String[] getMessage() {
			return message;
		}
		public void setMessage(String[] message) {
			this.message = message;
		}
		@Override
		public String toString() {
			return "Entity [id=" + id + ", message=" + Arrays.toString(message) + "]";
		}
		
		
		
	}

}
