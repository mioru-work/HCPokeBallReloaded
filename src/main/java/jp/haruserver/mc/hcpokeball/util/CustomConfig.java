package jp.haruserver.mc.hcpokeball.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class CustomConfig {
	private final Plugin plugin;
	private final File configFile;
	private final String file;
	private FileConfiguration config = null;

	public CustomConfig(Plugin plugin) {
		this(plugin, "config.yml");
	}

	public CustomConfig(Plugin plugin, String fileName) {
		this.plugin = plugin;
		this.file = fileName;
		configFile = new File(plugin.getDataFolder(), file);
	}
	public boolean isConfigExist() {
		if (!configFile.exists()) {
			return false;
		}else {
			return true;
		}
	}
	public void saveDefaultConfig() {
		if (!configFile.exists()) {
			plugin.saveResource(file, false);
		}
	}

	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(configFile);

		final InputStream defConfigStream = plugin.getResource(file);
		if (defConfigStream == null) {
			return;
		}

		config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, StandardCharsets.UTF_8)));
	}

	public FileConfiguration getConfig() {
		if (config == null) {
			reloadConfig();
		}
		return config;
	}

	public void saveConfig() {
		if (config == null) {
			return;
		}
		try {
			getConfig().save(configFile);
		} catch (IOException ex) {
			plugin.getLogger().log(Level.SEVERE,"Could not save config to " + configFile, ex);
		}
	}

}
