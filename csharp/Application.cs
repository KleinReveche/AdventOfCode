using System.Reflection;

namespace AdventOfCode;

class Application
{
    static void Main()
    {
        Console.WriteLine("Hello, World!");
        Console.WriteLine("Welcome to Advent of Code!");

        //2022
        TwentyTwo.DayOne.Solve();
        TwentyTwo.DayTwo.Solve();
        TwentyTwo.DayThree.Solve();
        TwentyTwo.DayFour.Solve();

        Console.WriteLine("\n");
        PrintTree(TwentyTwoTree);
    }

    public static List<List<TwentyTwoTreeLineContent>> TwentyTwoTree = new();
    public static List<TwentyTwoTreeLineContent> TwentyTwoTreeLine = new();
    public record class TwentyTwoTreeLineContent(ConsoleColor Color, string Content);

    public static string ReadInput(string name)
    {
        var assembly = Assembly.GetExecutingAssembly(); // Get File from Assembly
        Stream stream = assembly.GetManifestResourceStream("AdventOfCode.TwentyTwo.input." + name + ".txt")!;
        StreamReader reader = new(stream);
        return reader.ReadToEnd();
    }

    static void PrintTree(List<List<TwentyTwoTreeLineContent>> yearTree)
    {
        yearTree.Reverse();
        foreach (var line in yearTree)
        {
            foreach (var lineContent in line)
            {
                Console.ForegroundColor = lineContent.Color;
                Console.Write(lineContent.Content);
                Console.ResetColor();
            }
            Console.WriteLine();
        }
    }
}