def trova_numeri_primi(start, end):
	numeri_primi = []

	for num in range(start, end + 1):
		if num <= 1:
			continue

		print("new cycle")
	
		for i in range(2, num):
			if (num % i) == 0:
				print("break")
				break

		else:
			print("entered")
			numeri_primi.append(num)
	
	return numeri_primi

start = int(input("Inserisci il valore iniziale dell'intervallo: "))
end = int(input("Inserisci il valore finale dell'intervallo: "))

print(trova_numeri_primi(start, end))