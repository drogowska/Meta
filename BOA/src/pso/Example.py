from Functions import SphereFunction
from Functions import SchwefelFunction
from Functions import CigarFunction


class Example:


    def __init__(self, x) -> None:
        self._f = -1 
        self._x = x
        self._stop = 0

    def _count_fit(self, fun):
        if fun == 0:
            f = SphereFunction()
        elif fun == 1 :
            f = SchwefelFunction()
        else:
            f = CigarFunction()
        self._f = f._fun(self._x)
    
    def __gt__(self, other):
        if not isinstance(other, Example):
            return NotImplemented
        return self._f > other._f
    
