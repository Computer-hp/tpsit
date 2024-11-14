import numpy as np

L = 20
array1 = np.random.randint(1, 101, L)
array2 = np.random.randint(1, 101, L)

print(array1)
print(array2)

differenza = abs(array1[-1] - array2[-1])

# ...