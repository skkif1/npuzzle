package src.input;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputManager {

	private List<String> rowFile = new ArrayList<>();

	private List<String> map = new ArrayList<>();

	private final static char COMMENT_CHAR = '#';

	private int mapSize;

	private void getFileContent(String fileName) {
		try (Stream<String> linesStream = Files.lines(Paths.get(fileName))) {
			rowFile = linesStream.collect(Collectors.toList());
		} catch (IOException ex) {
			throw new InputManagerException("not valid file");
		}
	}


	private void cleanFile()
	{
		map = rowFile.stream()
				.map(InputManager::removeComment)
				.filter(this::isMapRow)
				.collect(Collectors.toList());
	}


	public int[][] getMapNumbers(String fileName) {
		getFileContent(fileName);
		findMapSize();
		cleanFile();

		int[][] numMap = new int[mapSize][mapSize];

		int i = 0;
		int j = 0;

		for (String line : map) {

			String[] nums = StringUtils.split(line, " ");

			for (String num : nums) {
				numMap[i][j++] = (Integer.parseInt(num));
			}
			j = 0;
			i++;
		}

		return numMap;
	}

	public int getMapSize() {
		return mapSize;
	}

	private void findMapSize() {
		List<String> rowSizeList = rowFile.stream().filter(StringUtils::isNumeric).collect(Collectors.toList());
		if (rowSizeList.size() == 1) {
			mapSize = Integer.parseInt(rowSizeList.get(0));
		} else {
			throw new InputManagerException("wrong map size");
		}
	}


	private boolean isValidLine(String line) {
		if (!StringUtils.isNumeric(line)) {
			line = removeComment(line);
			if (!StringUtils.isNumeric(line) && StringUtils.isNotEmpty(line)) {
				throw new InputManagerException("file content is not valid");
			}
		}
		return true;
	}


	private static String removeComment(String line) {
		if (StringUtils.contains(line, COMMENT_CHAR)) {
			return StringUtils.substringBefore(line, String.valueOf(COMMENT_CHAR));
		}
		return line;
	}

	private boolean isMapRow(String line) {
		String[] nums = StringUtils.split(line, ' ');

		if (nums.length == mapSize) {
			for (String num : nums) {
				if (!StringUtils.isNumeric(num)) {
					return false;
				}
			}
			return true;
		}

		return false;
	}

}
