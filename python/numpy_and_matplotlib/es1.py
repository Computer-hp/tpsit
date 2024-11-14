import numpy as np

def verify_array_order(array):
	if (np.all(array[:-1] <= array[1:])):
		print("I numeri dell'array sono in ordine crescente")
	elif (np.all(array[:-1] >= array[1:])):
		print("I numeri dell'array sono in ordine decrescente")
	else:
		print("Nessuno dei due")

array1 = np.array([1, 2, 3, 4, 5])
array2 = np.array([5, 4, 3, 2, 1])

verify_array_order(array1)
verify_array_order(array2)