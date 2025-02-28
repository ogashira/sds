package components.calcComponents;

import java.util.*;
import structures.*;

public class CreateNite {

	private List<StructSolDb> resultDb;

	public CreateNite(List<StructSolDb> resultDb) {
		this.resultDb = resultDb;
	}

	public List<StructNite> getNiteData() {

		List<StructNite> multiNiteData = new ArrayList<>();

		for (StructSolDb line : this.resultDb) {
			ScrapingNite scrapingNite = new ScrapingNite(line.casNo);
			StructNite structNite = scrapingNite.getStructNite();
			multiNiteData.add(structNite);
		}
		return multiNiteData;
	}

}
