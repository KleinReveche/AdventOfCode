namespace AdventOfCode.Lib;

public static class AdventConsole
{
    public static void WriteBanner()
    {
        Console.ForegroundColor = ConsoleColor.DarkYellow;
        Console.WriteLine("╔════════════════════════╗");
        Console.WriteLine("║                        ║");
        Console.WriteLine("║     Advent of Code     ║");
        Console.WriteLine("║                        ║");
        Console.WriteLine("╚════════════════════════╝\n");
        Console.ResetColor();
    }
    
    public static string GetUserInput(string prompt, Type type)
    {
        while (true)
        {
            Console.Write(prompt);
            var input = Console.ReadLine()?.Trim().ToLower();
            if (input is "exit" or "all") return input;
            if (type == typeof(int) && int.TryParse(input, out _)) return input;
            if (type == typeof(Year) && Enum.TryParse<Year>(input, true, out _)) return input;
        }
    }
    
    public static void HandleYearInput(string input)
    {
        if (input != "all") return;
        Console.Clear();
        WriteBanner();
        var solutions = SolutionRunner.GetSolutions(true, null);
        foreach (var solution in solutions) SolutionRunner.RunSolution(solution);
        Console.ReadKey(true);
    }
    
    public static void HandleDayInput(string input, string year, string day)
    {
        switch (year)
        {
            case "all":
            {
                Console.Clear();
                var solutions = SolutionRunner.GetSolutions(false, (Year)Enum.Parse(typeof(Year), year, true));
                foreach (var solution in solutions) SolutionRunner.RunSolution(solution);
                Console.ReadKey(true);
                return;
            }
            default:
                Console.Clear();
                WriteBanner();
                var solutionToRun = SolutionRunner.GetSolution(
                    (Year)Enum.Parse(typeof(Year), year), 
                    $"Day{int.Parse(day):00}");
                SolutionRunner.RunSolution(solutionToRun);
                Console.ReadKey(true);
                break;
        }
    }
}