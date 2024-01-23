package model;

public class mikiSolutionConf {
//    @FXML
//    private void updateConfigurations() {
//        useConfiguration.getItems().clear();
//
//        String currentDir = System.getProperty("user.dir");
//        File folder = new File(currentDir, "konfiguracje");
//
//        if (folder.exists() && folder.isDirectory()) {
//            File[] files = folder.listFiles();
//
//            if (files != null) {
//                for (File file : files) {
//                    if (file.isFile() && file.getName().toLowerCase().endsWith(".csv")) {
//                        useConfiguration.getItems().add(file.getName());
//                    }
//                }
//            }
//        }
//    }
//
//    public void useSetConfiguration() {
//        String filePath = "konfiguracje/" + useConfiguration.getValue();
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String[] columnTitles = reader.readLine().split(",");
//            String[] values = reader.readLine().split(",");
//
//            Map<String, String> dataMap = new HashMap<>();
//            for (int i = 0; i < columnTitles.length; i++) {
//                dataMap.put(columnTitles[i], values[i]);
//            }
//
//            height.setValue(Integer.parseInt(dataMap.get("Wysokość")));
//            width.setValue(Integer.parseInt(dataMap.get("Szerokość")));
//            numberOfAnimals.setValue(Integer.parseInt(dataMap.get("Liczba zwierząt")));
//            useTunnels.setSelected(Boolean.parseBoolean(dataMap.get("Występowanie tuneli")));
//            visibilityTunnels();
//            numberOfTunnels.setValue(Integer.parseInt(dataMap.get("Liczba tuneli")));
//            animalLife.setValue(Integer.parseInt(dataMap.get("Początkowa energia zwierząt")));
//            genotypeLength.setValue(Integer.parseInt(dataMap.get("Długość genotypu")));
//            useReverseGenotype.setSelected(Boolean.parseBoolean(dataMap.get("Odtwarzanie genotypu od tyłu")));
//            minimalEnergyToBreed.setValue(Integer.parseInt(dataMap.get("Min energii do rozmnażania")));
//            energyLossOnBreed.setValue(Integer.parseInt(dataMap.get("Utrata energii przy rozmnażaniu")));
//            maxGensToMutate.setValue(Integer.parseInt(dataMap.get("Max genów zmieniane w mutacji")));
//            minGensToMutate.setValue(Integer.parseInt(dataMap.get("Min genów zmieniane w mutacji")));
//            startingGrassNumber.setValue(Integer.parseInt(dataMap.get("Liczba traw na start")));
//            grassEnergy.setValue(Integer.parseInt(dataMap.get("Wartość energetyczna trawy")));
//            numberOfGrassPerDay.setValue(Integer.parseInt(dataMap.get("Liczba nowych traw na runde")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
