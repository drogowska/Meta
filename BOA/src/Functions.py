
class SphereFunction:

   def _fun(self, x) -> float:
      res = 0 
      for i in range(len(x)):
         res += x[i]*x[i]
      return res

class SchwefelFunction:
   
   def _fun(self, x) -> float:
    sum = 0;
    il = 0;
    for i in range(len(x)):
        sum += abs(x[i]*x[i])
        il *= abs(x[i])
    return sum + il
   
class CigarFunction:

   def _fun(self, x) -> float:
      sum = 0
      for i in range(1, len(x)-1, 1):
         sum += x[i]*x[i]
      return x[0]**2 + 10**6 * sum
