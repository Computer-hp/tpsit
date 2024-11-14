import numpy as np

def controlla_ordine(array):
	if (np.all(array[:-1] <= array[1:])):
		print("Ordine crescente")
	elif (np.all(array[:-1]) >= array[1:]):
		print("Ordine decrescente")
	else:
		print("Nessuno dei due")