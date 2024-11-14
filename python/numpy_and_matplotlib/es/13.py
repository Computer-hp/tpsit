def run():
	while True:
		stringa = str(input("Inserisci una stringa (inserisci 'exit' per terminare il programma): "))
		if stringa == "exit":
			return
		print("La lunghezza della stringa inserita e': ", len(stringa))

run()