from z3 import *
import time

def abs_z3(a):
    """Get the absolute value of a Z3 variable"""
    return If(a >= 0, a, -a)

# Number of Queens
print("N: ")
N = int(input())

start = time.time()
# Variables
X = [Int("col_%s" % col) for col in range(N)]

# Constraints
domain = [And(X[col] >= 1, X[col] <= N) for col in range(N)]


queenConst = [And(X[i] != X[j], abs_z3(X[j]-X[i]) != abs(j-i))
            for i in range(N-1)
            for j in range(i+1, N)]

s = Solver()
s.add(domain + queenConst)

if s.check() == sat:
    m = s.model()
    r = [m.evaluate(X[i]) for i in range(N)]
    print(r)

print("Elapsed time: ", time.time() - start, "s")