package components.calcComponents;

import structures.*;
import java.util.*;

public class PhysicalData {

	private List<StructSolDb> listStructSol;

	public PhysicalData(List<StructSolDb> listStructSol) {
		this.listStructSol = listStructSol;
	}

	public List<StructPhysicalData> createListPhysicalData() {

		IFPhysicalValue[] objItems = {
				new ImpMinValue("引火点"),
				new ImpMinValue("発火点"),
				new ImpMinValue("爆発限界下限値"),
				new ImpMaxValue("爆発限界上限値"),
				new ImpMinValue("沸点"),
				new ImpMaxValue("蒸気圧"),
				new ImpMaxValue("蒸気比重"),
				new ImpMaxValue("蒸発速度"),
				new ImpMinValue("OSHA PEL"),
				new ImpMinValue("ACGIH TWA"),
				new ImpMinValue("日本産業衛生学会"),
				new ImpMinValue("急性毒性LD50")
		};

		List<StructPhysicalData> listPhysicalData = new ArrayList<>();

		for (IFPhysicalValue objItem : objItems) {
			StructPhysicalData structPhysicalData = objItem.getStructPhysicalData(this.listStructSol);
			listPhysicalData.add(structPhysicalData);
		}

		return listPhysicalData;

	}

	/*
	    PhysicalValue  = new ImpMinValue("引火点");
	    PhysicalValue  = new ImpMinValue("発火点");
	    PhysicalValue  = new ImpMinValue("爆発限界下限値");
	    PhysicalValue  = new ImpMaxValue("爆発限界上限値");
	    PhysicalValue  = new ImpMinValue("沸点");
	    PhysicalValue  = new ImpMaxValue("蒸気圧");
	    PhysicalValue  = new ImpMaxValue("蒸気比重");
	    PhysicalValue  = new ImpMaxValue("蒸発速度");
	    PhysicalValue  = new ImpMinValue("OSHA PEL");
	    PhysicalValue  = new ImpMinValue("ACGIH TWA");
	    PhysicalValue  = new ImpMinValue("日本産業衛生学会");
	    PhysicalValue  = new ImpMinValue("急性毒性LD50");
	    
	    
	        
	    
	    
	
	}
	
	
	public List<StructPhysicalData> getListPhysicalData(){
	
	
	
	
	}
	
	
	private StructPhysicalData getMinData() {
	
	}
	*/

}
