namespace AdventOfCode.TwentyTwo.Day06;

[Problem(Year.TwentyTwo, "Day06", "Tuning Trouble")]
public class Solution(string input) : ISolution
{
    public object PartOne()
    {
        var startPacketIndex = ProcessData(4);
        return $"The characters that were needed to be processed before the first start-of-pocket were {startPacketIndex}";
    }

    public object PartTwo()
    {
        var startMessageIndex = ProcessData(14);
        return $"The characters that were needed to be processed before the first start-of-message were {startMessageIndex}\n";
    }

    private int ProcessData(int charNum)
    {
        var data = input.Trim();
        var index = 0;

        while (index + charNum < data.Length)
        {
            var subString = data.Substring(index, charNum);
            if (subString.Distinct().Count() == charNum) return index + charNum;
            index++;
        }

        return -1;
    }
}