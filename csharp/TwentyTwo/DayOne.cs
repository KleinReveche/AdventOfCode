using static AdventOfCode.Application;

namespace AdventOfCode.TwentyTwo;

class DayOne
{
    internal static void Solve()
    {
        var records = new List<int>();
        var currentSum = 0;

        string input = Application.ReadInput("day1");
        
        ReadRecords(records, currentSum, input);

        int max = records.Max();
        List<int> recordsDesc = records.ToList();
        recordsDesc.Sort();
        recordsDesc.Reverse();

        var top3TotalCalories = recordsDesc[0] + recordsDesc[1] + recordsDesc[2];

        Console.WriteLine("\n --- 2022 Day 1: Calorie Counting ---\n");
        Console.WriteLine($"   The Elf with most calories carried was Elf {records.IndexOf(max)} with {max}");

        Console.WriteLine("   Top 3 Elves carrying the most calories: " +
        $"Elf {records.IndexOf(recordsDesc[0])}, " +
        $"Elf {records.IndexOf(recordsDesc[1])}, " +
        $"Elf {records.IndexOf(recordsDesc[2])} " +
        $"with a combined {top3TotalCalories}\n");

        List<TwentyTwoTreeLineContent> dayOneLine = new()
        {
            new TwentyTwoTreeLineContent(ConsoleColor.Gray, "  ~    ~  ~      ~     ~ ~   ~     ~  ~  ~   ~   ")
        };
        TwentyTwoTree.Add(dayOneLine);
    }

    private static int ReadRecords(List<int> records, int currentSum, string input)
    {
        StringReader inputLines = new(input);

        while (true)
        {
            var line = inputLines.ReadLine();

            if (line != null)
            {
                if (string.IsNullOrWhiteSpace(line))
                {
                    records.Add(currentSum);
                    currentSum = 0;
                }
                else
                {
                    currentSum += int.Parse(line);
                }
            }
            else break;
        }

        return currentSum;
    }
}