import numpy as np
import matplotlib.pyplot as plt

x = np.logspace(0, 2, 100)
y = np.log(x)

plt.semilogx(x, y, label = "y = log(x)")
plt.xlabel('x')
plt.ylabel('y')

plt.grid(True)
plt.title("Title")
plt.legend()

plt.show()