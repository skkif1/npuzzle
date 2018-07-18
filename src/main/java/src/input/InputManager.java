package src.input;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputManager {

	private List<String> rowFile = new ArrayList<>();

	private List<String> map = new ArrayList<>();

	private final static char COMMENT_CHAR = '#';
	private final static String COMMENT_STRING = "#";

	private int mapSize;

	private void getFileContent(String fileName) {
		try (Stream<String> linesStream = Files.lines(Paths.get(fileName))) {
			rowFile = linesStream.collect(Collectors.toList());
		} catch (IOException ex) {
			throw new InputManagerException("not valid file " + fileName);
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
		try {
			validateFile();
		} catch (NumberFormatException e) {
			throw new InputManagerException("Invalid file");
		}
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

	public void setMapSize(int mapSize){
		this.mapSize = mapSize;
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

	private void validateFile() throws NumberFormatException {
		if (rowFile == null || rowFile.isEmpty()) throw new InputManagerException("File is absent or empty file");
		Set<Integer> numbers = new HashSet<>();
		String line;
		int i;
		int sum = 0;
		Integer size = null;
		for (String s : rowFile) {
			if (StringUtils.startsWith(s.trim(), COMMENT_STRING)) continue;
			line = removeComment(s).trim();
			String[] split = line.split("[\\s]+");
			if (split.length == 1) {
				if (size != null) throw new InputManagerException("Invalid size");
				else size = Integer.parseInt(split[0]);
				if (size <= 2) throw new InputManagerException("Wrong map size");
				continue;
			}
			for (String s1 : split) {
				i = Integer.parseInt(s1);
				if (i < 0) throw new InputManagerException("Numbers must be more than zero");
				numbers.add(i);
			}
		}
		if (size == null || numbers.size() != size * size) throw new InputManagerException("Invalid numbers");
		for (Integer number : numbers) {
			sum += number;
		}
		if (sum != (size * size - 1) * (size * size) / 2) throw new InputManagerException("Invalid numbers");
	}

}
