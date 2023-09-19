using System.Reflection;

namespace AdventOfCode;

public static class Application
{
    public static void Main()
    {
        Console.WriteLine("Hello, World!");
        Console.WriteLine("Welcome to Advent of Code!");

        //2022
        TwentyTwo.DayOne.Solve();
        TwentyTwo.DayTwo.Solve();
        TwentyTwo.DayThree.Solve();
        TwentyTwo.DayFour.Solve();

        Console.WriteLine("\n");
    }

    public static string ReadInput(string name)
    {
        var assembly = Assembly.GetExecutingAssembly(); // Get File from Assembly
        var stream = assembly.GetManifestResourceStream($"AdventOfCode.TwentyTwo.inputs.{name}.txt")!;
        StreamReader reader = new(stream);
        return reader.ReadToEnd();
    }
}