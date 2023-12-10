namespace AdventOfCode.TwentyTwo.Day03;

[Problem(Year.TwentyTwo, "Day03", "Rucksack Reorganization")]
public class Solution(string input) : ISolution
{
    private record RucksackItems(string FirstHalf, string SecondHalf, string Combined);
    
    public object PartOne()
    {
        var sum = Solve(false);
        return $"The sum of all item type priorities of all the Elves' rucksack are: {sum}";
    }

    public object PartTwo()
    {
        var groupedSum = Solve(true);
        return $"Also, all item type priorities of three grouped Elves' rucksack are: {groupedSum}";
    }
    
    private int Solve(bool isGrouped)
    {
        var sum = 0;
        var groupedSum = 0;
        List<RucksackItems> rucksackItems = [];
        List<List<RucksackItems>> groupedRucksackItems = [];
        ReadData(input, rucksackItems);

        sum += rucksackItems.Sum(item => CheckForCommonLetter(item.FirstHalf, item.SecondHalf));

        for (var i = 0; i < rucksackItems.Count; i++)
        {
            if ((i + 1) % 3 != 0) continue;
            List<RucksackItems> grouped =
            [
                rucksackItems[i - 2],
                rucksackItems[i - 1],
                rucksackItems[i]
            ];
            groupedRucksackItems.Add(grouped);
        }

        groupedSum += groupedRucksackItems.Sum(groupedItems => CheckForCommonLetter(groupedItems[0].Combined, groupedItems[1].Combined, groupedItems[2].Combined));
        return isGrouped ? groupedSum : sum;
    }

    private static void ReadData(string input, ICollection<RucksackItems> rucksackItems)
    {
        var items = input.Trim().Split('\n');

        foreach (var item in items)
        {
            var middleLineIndex = item.Length / 2;
            var firstHalf = item[..middleLineIndex];
            var secondHalf = item[middleLineIndex..];
            rucksackItems.Add(new RucksackItems(firstHalf, secondHalf, item));
        }
    }

    private static int CheckForCommonLetter(params string[] strings)
    {
        // ReSharper disable once CoVariantArrayConversion
        IEnumerable<char>[] charArrays = strings.Select(item => item.ToCharArray().ToHashSet()).ToArray();
        var commonLetter = charArrays.Aggregate((acc, set) => acc.Intersect(set));

        var enumerable = commonLetter as char[] ?? commonLetter.ToArray();
        return enumerable.First() switch
        {
            >= 'a' and <= 'z' => enumerable.First() - 'a' + 1,
            >= 'A' and <= 'Z' => enumerable.First() - 'A' + 27,
            _ => throw new ArgumentException($"Invalid character: {enumerable.First()}")
        };
    }
}