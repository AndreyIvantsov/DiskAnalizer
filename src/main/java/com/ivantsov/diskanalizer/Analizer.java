package com.ivantsov.diskanalizer;

import java.util.HashMap;
import java.util.Map;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Analizer {
	
	private HashMap<String, Long> sizes = new HashMap<>();
	
	public Map<String, Long> calculateDirectorySize(Path path) {
		try {
			Files.walkFileTree(
					path, 
					new SimpleFileVisitor<>() {
						@Override
						public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
							long size = Files.size(file);
							updateDirSize(file, size);
							return FileVisitResult.CONTINUE;
						}
						
						@Override
						public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
							return FileVisitResult.SKIP_SUBTREE;
						}
					}
			);
			return sizes;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void updateDirSize(Path file, Long size) {
		String key = file.toString();
		sizes.put(key, size + sizes.getOrDefault(key, 0L));
		
		Path parent = file.getParent();
		
		if (parent != null) {
			updateDirSize(parent, size);
		}
	}
}
