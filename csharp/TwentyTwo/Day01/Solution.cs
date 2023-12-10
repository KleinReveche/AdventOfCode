namespace AdventOfCode.TwentyTwo.Day01;

[Problem(Year.TwentyTwo, "Day01", "Calorie Counting")]
public class Solution(string input) : ISolution
{
    private readonly List<int> _records = ReadRecords(input);

    public object PartOne()
    {
        var max = _records.Max();
        return $"The Elf with most calories carried was Elf {_records.IndexOf(max)} with {max}";
    }

    public object PartTwo()
    {
        var recordsDesc = _records.ToList();
        recordsDesc.Sort();
        recordsDesc.Reverse();
        var top3TotalCalories = recordsDesc[0] + recordsDesc[1] + recordsDesc[2];
        return $"Top 3 Elves carrying the most calories: Elf {_records.IndexOf(recordsDesc[0])}, Elf {_records.IndexOf(recordsDesc[1])}, Elf {_records.IndexOf(recordsDesc[2])} with a combined {top3TotalCalories}";
    }

    private static List<int> ReadRecords(string input)
    {
        var lines = input.Split("\n\n", StringSplitOptions.RemoveEmptyEntries);
        return lines.Select(line => line.Split('\n').Select(int.Parse).Sum()).ToList();
    }
}