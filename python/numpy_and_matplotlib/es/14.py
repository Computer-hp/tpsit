def controlla_congruenza_liste(list1, list2):
	for el1 in list1:
		if el1 not in list1:
			return False
	
	return True
