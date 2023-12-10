global using AdventOfCode.Lib;
namespace AdventOfCode;

public static class Application
{
    public static void Main()
    {
        Console.Title = "Advent of Code";
        Console.WriteLine("Hello, World!");
        Console.WriteLine("Welcome to Advent of Code!\n");

#if DEBUG
        SolutionRunner.RunWorkInProgressSolution();
#endif

        var year = GetUserInput("Year to Solve: ", typeof(Year));
        if (year == "exit") return;
        if (year == "all")
        {
            Console.Clear();
            var solutions = SolutionRunner.GetSolutions(true, null);
            foreach (var solution in solutions) SolutionRunner.RunSolution(solution);
            Console.ReadKey(true);
            return;
        }
        
        var day = GetUserInput("Day to Solve [1-25]: ", typeof(int));
        switch (day)
        {
            case "all":
            {
                Console.Clear();
                var solutions = SolutionRunner.GetSolutions(false, (Year)Enum.Parse(typeof(Year), year, true));
                foreach (var solution in solutions) SolutionRunner.RunSolution(solution);
                Console.ReadKey(true);
                return;
            }
            case "exit":
                return;
            default:
                var solutionToRun = SolutionRunner.GetSolution(
                    (Year)Enum.Parse(typeof(Year), year), 
                    $"Day{int.Parse(day):00}");
                SolutionRunner.RunSolution(solutionToRun);
                Console.ReadKey(true);
                break;
        }
    }

    private static string GetUserInput(string prompt, Type type)
    {
        while (true)
        {
            Console.Write(prompt);
            var input = Console.ReadLine()?.ToLower();

            if (input is "all" or "exit") return input;

            if (type == typeof(Year) && Enum.TryParse(input, true, out Year year) &&
                Enum.IsDefined(typeof(Year), year)) return input;

            if (type == typeof(int) && int.TryParse(input, out var dayInt) && dayInt is >= 1 and <= 25) return input;
        }
    }
}