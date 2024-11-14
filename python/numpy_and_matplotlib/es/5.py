import numpy as np

def calcola_somma(array):
	return np.sum(array)

L = 20
array = np.random.randint(1, 101, L)
print(calcola_somma(array))