/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package jobs4u.base.app.backoffice.console.presentation.customermanager;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.applicationmanagement.domain.WordAnalysis;
import jobs4u.base.applicationmanagement.domain.dto.ApplicationDTO;
import jobs4u.base.candidatemanagement.application.DisplayCandidateController;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.notificationmanagement.domain.MessageDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DisplayCandidateUI extends AbstractUI {

    private final DisplayCandidateController theController = new DisplayCandidateController();

    @Override
    protected boolean doShow() {
        List<Candidate> candidateList = theController.displayCandidates();
        if(candidateList.isEmpty()){
            System.out.println("There are no candidates.");
            return false;
        }
        System.out.println("Choose a candidate:");
        final Candidate candidate = showCandidateList(candidateList);
        if(candidate.identity() == null) return false;

        try{
            final Set<String> files = displayInformation(candidate);
            if(files.isEmpty()) return false;
            displayWords(theController.getWordCount(files));
        }catch(Exception e){
            System.out.println(e);
        }


        return false;
    }

    public Candidate showCandidateList(List<Candidate> candidateList){
        int i = 0;
        boolean validOption = false;
        for (Candidate candidate : candidateList) {
            System.out.println(i++ + 1 + " - " + candidate.associatedUser().name());
        }
        System.out.println("0 - Exit");

        do{
            i = Console.readInteger("Option:");
            if(i == 0) return new Candidate();
            if((i-1) >= candidateList.size()) System.out.println("Invalid option");
            else validOption = true;
        }while(!validOption);

        return candidateList.get(i - 1);
    }

    public Set<String> displayInformation(Candidate candidate){
        int i = 0;
        boolean validOption = false;
        List<ApplicationDTO> DTO = theController.getCandidateApplications(candidate.identity());
        System.out.println("\nName: " + candidate.associatedUser().name());
        System.out.println("E-mail: " + candidate.associatedUser().email());
        System.out.println("Phone number: " + candidate.identity());
        System.out.println("\nChoose a job application to analyze:");
        System.out.printf("%-15s%-15s%-15s%n", "Nº", "Reference", "State");
        for(ApplicationDTO app : DTO){
            System.out.printf("%-15s%-15s%-15s%n", ++i, app.getJobRefCode(), app.getState());
        }
        System.out.println("Type 0 to exit");

        do{
            i = Console.readInteger("Option:");
            if(i == 0) return null;
            if((i-1) >= DTO.size()) System.out.println("Invalid option");
            else validOption = true;
        }while(!validOption);

        String[] DTOFiles = DTO.get(i - 1).getAttachedFile().split("\n");
        Set<String> files = new HashSet<>();
        for(String file : DTOFiles){
            files.add(DTO.get(i - 1).getFilesPath() + file);
        }

        return files;
    }

    public void displayWords(List<WordAnalysis.WordInfo> words){
        int i = 0;
        System.out.println("\nFirst 20 most common words used: ");
        System.out.printf("%-10s%-10s%-10s%n", "Rank", "Word", "Count");
        for(WordAnalysis.WordInfo word : words){
            if(i++ == 20) break;
            System.out.printf("%-10s%-10s%-10s%n", i, word.getWord(), word.getCount());
        }
    }

    @Override
    public String headline() {
        return "List Candidate Info";
    }
}
