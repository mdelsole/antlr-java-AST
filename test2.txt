program mytests;
VAR
    k : real = 8;
    x : real = 6;

function max(num1, num2: real): real;
VAR
   result : real;

BEGIN
   if (num1 > num2) then
      result := num1;

   else
      result := num2;
   max := result;
END;

BEGIN

    x := max(3, 4);
    writeln(x);

END;