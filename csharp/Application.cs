global using AdventOfCode.Lib;
namespace AdventOfCode;

public static class Application
{
    public static void Main()
    {
        Console.Title = "Advent of Code";
        AdventConsole.WriteBanner();

#if DEBUG
        SolutionRunner.RunWorkInProgressSolution();
#endif

        var year = AdventConsole.GetUserInput("Year to Solve: ", typeof(Year));
        if (year is "exit") return;
        AdventConsole.HandleYearInput(year);
        
        var day = AdventConsole.GetUserInput("Day to Solve [1-25]: ", typeof(int));
        if (day is "exit") return;
        AdventConsole.HandleDayInput(day, year, day);
    }
}