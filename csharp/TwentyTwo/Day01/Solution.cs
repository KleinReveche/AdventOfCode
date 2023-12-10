namespace AdventOfCode.TwentyTwo.Day01;

[Problem(Year.TwentyTwo, "Day01", "Calorie Counting")]
public class Solution(string input) : ISolution
{
    private readonly List<int> _records = [];

    public object PartOne()
    {
        ReadRecords(_records, input);
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

    private static void ReadRecords(List<int> records, string input)
    {
        var currentSum = 0;
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
            else
            {
                break;
            }
        }
    }
}