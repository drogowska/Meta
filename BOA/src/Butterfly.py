import random
from Functions import SchwefelFunction, SphereFunction, CigarFunction
import numpy as np

class Butterfly:

    def __init__(self, dim, fro, to, fun, p=0.8, c=0.01, a=0.1):
        self._x = []
        self._fragrance = -1 
        self._fun = fun
        self._c = c #modalnosc sensoryczna
        self._l = 0  #intensywnosc bodzca [0,1]
        self._a = a #wykładnik mocy
        self._best_f = self._fragrance
        self._p = p
        for i in range(dim):
            self._x.append(random.uniform(fro, to))
        self._best_x = self._x

    def _cal_fragrance(self):
        self._cal_l()
        self._fragrance = self._c*(self._l**self._a)
        

    def _update_position(self, g, populaton):
        old = self._fragrance
        old_x = self._x.copy()
        for i in range(len(self._x)):
            r = random.random()
            r1 = random.random()
            rand = random.random()
            if rand < self._p :
                self._x[i] += (r*r1 + g._x[i] - self._x[i]) * self._fragrance   #global
            else:
                rnd = populaton._butters[random.randint(0, len(populaton._butters)-1)]._x[i]
                rnd2 = populaton._butters[random.randint(0, len(populaton._butters)-1)]._x[i]
                self._x[i] += (r*r1 + rnd - rnd2) * self._fragrance   #local
        self._cal_fragrance()

        if old < self._fragrance:
             self._fragrance = old
             self._x = old_x



    def _cal_l(self):
        if self._fun == 0:
            f = SphereFunction()
        elif self._fun == 1 :
            f = SchwefelFunction()
        else:
            f = CigarFunction()
        self._l = f._fun(self._x)
        if self._l < 0:
            self._l *= -1

    def __eq__(self, __value: object) -> bool:
         return self._x == __value._x
    
    def __gt__(self, other) -> bool:
        if not isinstance(other, Butterfly):
            return NotImplemented
        return self._fragrance > other._fragrance
    
