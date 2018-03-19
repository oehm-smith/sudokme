public void runRow() {
	for (Row r : rows) {
		if (fillInRow(r) == false) {
			not solved;
		}
	}
}

public boolean fillInRow(Row r) {
	for (Cell c : r.emptyCells())	{
		
		List<Integer> I = Intersection(r.available(), r.col.available(), r.square.available());
		if (I.size() == 1)
		{
			insert I.get(0) into r.cell;
			return true;
		} else {
			for (Integer potentialValue : I)	{
				insert potentialValue into cell;
				if (fillInRow(r) == true) {
					return true;
				} else {
					setcellvalue = 0;
					return false;
			}
		}
	}