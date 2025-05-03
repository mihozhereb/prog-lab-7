package ru.mihozhereb.control;

import ru.mihozhereb.collection.model.*;
import ru.mihozhereb.io.ConsoleWorker;
import ru.mihozhereb.io.Formatters;

import java.time.LocalDate;;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

/**
 * Builder class of {@code MusicBand} object
 * <p>
 * Collect user input and validate the data
 */
public class InputHelper {
    private final ConsoleWorker consoleWorker;
    private final MusicBand musicBand;
    private boolean infiniteInput = true;

    InputHelper(ConsoleWorker cw) {
        this.musicBand = new MusicBand();
        this.musicBand.setCoordinates(new Coordinates());
        this.musicBand.setFrontMan(new Person());
        this.consoleWorker = cw;

        if (java.io.FileInputStream.class.equals(System.in.getClass())) {
            infiniteInput = false;
        }
    }

    private String getConsoleInput() throws InputCancelledException {
        String text = consoleWorker.read();
        if (java.io.FileInputStream.class.equals(System.in.getClass())) {
            consoleWorker.writeLn(text);
        }
        if (text == null) {
            throw new InputCancelledException("Input is cancelled.");
        }
        return text;
    }

    private void inputName() throws InputCancelledException {
        while (true) {
            consoleWorker.writeLn("Enter MusicBand's name: ");
            try {
                musicBand.setName(getConsoleInput());
            } catch (IllegalArgumentException e) {
                String errorMessage = "Invalid argument. " + e.getLocalizedMessage();
                if (infiniteInput) {
                    consoleWorker.writeLn(errorMessage);
                    continue;
                } else {
                    throw new InputCancelledException(errorMessage);
                }
            }
            break;
        }
    }

    private void inputX() throws InputCancelledException {
        while (true) {
            consoleWorker.writeLn("Enter MusicBand's X coordinate: ");
            try {
                musicBand.getCoordinates().setX(Double.valueOf(getConsoleInput()));
            } catch (IllegalArgumentException e) {
                String errorMessage = "Invalid argument. " + e.getLocalizedMessage();
                if (infiniteInput) {
                    consoleWorker.writeLn(errorMessage);
                    continue;
                } else {
                    throw new InputCancelledException(errorMessage);
                }
            }
            break;
        }
    }

    private void inputY() throws InputCancelledException {
        while (true) {
            consoleWorker.writeLn("Enter MusicBand's Y coordinate: ");
            try {
                musicBand.getCoordinates().setY(Float.valueOf(getConsoleInput()));
            } catch (IllegalArgumentException e) {
                String errorMessage = "Invalid argument. " + e.getLocalizedMessage();
                if (infiniteInput) {
                    consoleWorker.writeLn(errorMessage);
                    continue;
                } else {
                    throw new InputCancelledException(errorMessage);
                }
            }
            break;
        }
    }

    private void inputNumberOfParticipants() throws InputCancelledException {
        while (true) {
            consoleWorker.writeLn("Enter MusicBand's number of participants: ");
            try {
                musicBand.setNumberOfParticipants(Long.parseLong(getConsoleInput()));
            } catch (IllegalArgumentException e) {
                String errorMessage = "Invalid argument. " + e.getLocalizedMessage();
                if (infiniteInput) {
                    consoleWorker.writeLn(errorMessage);
                    continue;
                } else {
                    throw new InputCancelledException(errorMessage);
                }
            }
            break;
        }
    }

    private void inputGenre() throws InputCancelledException {
        while (true) {
            String options = Arrays.toString(MusicGenre.values());
            consoleWorker.writeLn("Enter MusicBand's genre " + options + ": ");
            try {
                String value = getConsoleInput();
                if (value.equals("null") || value.isEmpty()) break;
                musicBand.setGenre(MusicGenre.valueOf(value));
            } catch (IllegalArgumentException e) {
                String errorMessage = "Invalid argument. " + e.getLocalizedMessage();
                if (infiniteInput) {
                    consoleWorker.writeLn(errorMessage);
                    continue;
                } else {
                    throw new InputCancelledException(errorMessage);
                }
            }
            break;
        }
    }

    private void inputFrontManName() throws InputCancelledException {
        while (true) {
            consoleWorker.writeLn("Enter FrontMan's name: ");
            try {
                musicBand.getFrontMan().setName(getConsoleInput());
            } catch (IllegalArgumentException e) {
                String errorMessage = "Invalid argument. " + e.getLocalizedMessage();
                if (infiniteInput) {
                    consoleWorker.writeLn(errorMessage);
                    continue;
                } else {
                    throw new InputCancelledException(errorMessage);
                }
            }
            break;
        }
    }

    private void inputBirthday() throws InputCancelledException {
        while (true) {
            consoleWorker.writeLn("Enter FrontMan's birthday (format \"" +
                    LocalDate.now().format(Formatters.DATE.get()) + "\"): ");
            try {
                musicBand.getFrontMan().setBirthday(LocalDate.parse(getConsoleInput(), Formatters.DATE.get()));
            } catch (IllegalArgumentException e) {
                String errorMessage = "Invalid argument. " + e.getLocalizedMessage();
                if (infiniteInput) {
                    consoleWorker.writeLn(errorMessage);
                    continue;
                } else {
                    throw new InputCancelledException(errorMessage);
                }
            } catch (DateTimeParseException e) {
                String errorMessage = "Invalid format. Format must be like \"" +
                        LocalDate.now().format(Formatters.DATE.get()) + "\"";
                if (infiniteInput) {
                    consoleWorker.writeLn(errorMessage);
                    continue;
                } else {
                    throw new InputCancelledException(errorMessage);
                }
            }
            break;
        }
    }

    private void inputHeight() throws InputCancelledException {
        while (true) {
            consoleWorker.writeLn("Enter FrontMan's height: ");
            try {
                String value = getConsoleInput();
                if (value.equals("null") || value.isEmpty()) break;
                musicBand.getFrontMan().setHeight(Double.valueOf(value));
            } catch (IllegalArgumentException e) {
                String errorMessage = "Invalid argument. " + e.getLocalizedMessage();
                if (infiniteInput) {
                    consoleWorker.writeLn(errorMessage);
                    continue;
                } else {
                    throw new InputCancelledException(errorMessage);
                }
            }
            break;
        }
    }

    private void inputWeight() throws InputCancelledException {
        while (true) {
            consoleWorker.writeLn("Enter FrontMan's weight: ");
            try {
                musicBand.getFrontMan().setWeight(Integer.parseInt(getConsoleInput()));
            } catch (IllegalArgumentException e) {
                String errorMessage = "Invalid argument. " + e.getLocalizedMessage();
                if (infiniteInput) {
                    consoleWorker.writeLn(errorMessage);
                    continue;
                } else {
                    throw new InputCancelledException(errorMessage);
                }
            }
            break;
        }
    }

    private void inputHairColor() throws InputCancelledException {
        while (true) {
            String options = Arrays.toString(Color.values());
            consoleWorker.writeLn("Enter FrontMan's hair color " + options + ": ");
            try {
                String value = getConsoleInput();
                if (value.equals("null") || value.isEmpty()) break;
                musicBand.getFrontMan().setHairColor(Color.valueOf(value));
            } catch (IllegalArgumentException e) {
                String errorMessage = "Invalid argument. " + e.getLocalizedMessage();
                if (infiniteInput) {
                    consoleWorker.writeLn(errorMessage);
                    continue;
                } else {
                    throw new InputCancelledException(errorMessage);
                }
            }
            break;
        }
    }

    /**
     * Collect all data from user
     *
     * @return built MusicBand object
     * @throws InputCancelledException If user cancel input
     */
    public MusicBand input() throws InputCancelledException {
        inputName();
        inputX();
        inputY();
        inputNumberOfParticipants();
        inputGenre();
        inputFrontManName();
        inputBirthday();
        inputHeight();
        inputWeight();
        inputHairColor();

        return musicBand;
    }
}
