package com.kleinreveche.adventofcode.twentytwo

//--- Day 3: Rucksack Reorganization --- https://adventofcode.com/2022/day/3
fun dayThree() {
    val data = readDayThreeData(dayThreeInput)
    val groupedData = data.chunked(3)
    var sum = 0
    var groupedSum = 0

    data.forEach {
        val commonLetterValue = checkForCommonLetter(it.item1, it.item2)
        sum += commonLetterValue
    }

    groupedData.forEach {
        val commonGroupedLetterValue = checkForCommonLetter(it[0].combined, it[1].combined, it[2].combined)
        groupedSum += commonGroupedLetterValue
    }

    println("--- Day 3: Rucksack Reorganization ---")
    println("The sum of all item type priorities of all the Elves' rucksack are: $sum")
    println("Also, all item type priorities of three grouped Elves' rucksack are: $groupedSum")
    println()
}

private data class DayThreeItemList(val item1: String, val item2: String, val combined: String)

private fun checkForCommonLetter(vararg strings: String): Int {
    val charArrays = strings.map { it.toCharArray().toSet() }
    val commonLetter = charArrays.reduce { acc, set -> acc.intersect(set) }
    return when (commonLetter.first()) {
        in 'a'..'z' -> commonLetter.first() - 'a' + 1
        in 'A'..'Z' -> commonLetter.first() - 'A' + 27
        else -> throw IllegalArgumentException("Invalid character: $commonLetter.first()")
    }
}
private fun readDayThreeData(input: String): List<DayThreeItemList> {
    val items = input.trim().lines()
    val separatedItems = mutableListOf<DayThreeItemList>()
    var firstHalf: String
    var secondHalf: String

    items.forEach { item ->
        val middleLineIndex = item.count() / 2
        firstHalf = item.substring(0, middleLineIndex)
        secondHalf = item.substring(middleLineIndex, item.count())
        separatedItems.add(DayThreeItemList(firstHalf, secondHalf, item))
    }

    return separatedItems
}

private val dayThreeInput = """
    JppMDcJPcQbqGqFb
    ZStgnHtsSjGBhqFbBmsm
    djzzwgHHggdnfwjtMPDPMGpPlNfpLDll
    dRCtwtlCSttPtlNPNtgvPrDqmBsjGSpjBBsJsqqmrp
    ZhWnZhzMMfnWWTDzBrmsmjsBccJB
    TFQMrZrMfZrLZZnLQdlvNdCgdHPlCllHNF
    PVddPnZWDDPqmHZzqVPzqdHdMRMJjQtvmvjvtTQQtlRtbbQl
    fsNswFpChpNwfgtMvCbjTRJBbtRj
    SFgfSMShLcsLgNMFhpwShFFZPHqZGZPqHdzPZHZcdzDDqd
    dHffzCqSfJCCzCPvdcpzRrBDDBSBBBMtthDDrFMT
    bVsZbsrgbWshBTFtDTtZTD
    slWGNbNWgWwnmNnwgnlGjdJJdPHcvcrcHjqPzdqPvp
    vBFBzGvGBvjFpGWcvCCvBvGPPhZbgLhMnhrPgFgPFgLLPF
    dVlslfdJNVJJmQQwdNRNwLZZZrrqhbPbZndpdgMhLP
    HJRHJpNJlVmftHfNQNRsHmmGCtGWjGDDTvCzcjWDStvcvT
    cVQzCCCpVVjgHsNwwFRqSRSRRtNH
    PWfhfPhbWWdWllPDdTvdbnvhLJLLMJFTSNwJtMRwJLqJJNMq
    DnfblllPbDrbWmbdPFdvlhdgQzzZZzQgcpcsZGcGQQgcpr
    hdFnnrdLnJRrZMzVlMbrwZvz
    TgfjGCcfsqfqqBfRMPDVvVVvTZRPbP
    qQqffcGStBSCQcsqCCBRjGqWhHFptFdnhNLmhnLWmphLLt
    WHPqWhLWHBMqddCdtDRwStDSwgnw
    rfCfZTmvpvvpFVfFrbvTbvcgtwgtStntggwbSJttgJtwgw
    fpQFsmfFmfZcrFCMhWNQWLCzhhCM
    zdqqJHDWwqNNZjQSmtdjpmfnjj
    GFBcPfBLPThcTcLRbpTQQjpTQrjSnQjlvn
    MFVLcsPVbPcRGfVPFRgDzJsHZNDCZNCNZsHg
    WQdMhlmQMwfZlQdgdHfddvtzmbJbcsctbBJqqcvqzB
    rSTrDDCSSGRCnnSGwwzJsbBvJsCCttccsvsz
    VnFwDrNwPwVSDrLgjfZZdMQFMWgljQ
    rJNjBLNQjvLQjFFLQJCFTvGWlRRgfGWDWlCDzlGnRRWG
    tMMhShhwhbgplflwDlnnnzDH
    ZcSZVMSqshhQBQqQvqQgjJ
    btTtWGbvtcvHHccdNRhHdl
    TDzqLPSqBzqVSldPddHhhNNlRQ
    jBLDqDgZZBzJJmsbWjppmmpTWw
    LLDTRRfTwZRGZfDCRTwRsVHsVFBVZVlmVFBFlFJl
    QjWpQrzQcbhfMqrVVHVPFslFVpVmHH
    hqhzqMfNvjvMMzhdRDdCwGDSSdnvRn
    jmMdNCLjLmJPtHtSHhSVVJ
    pDBqgBgCvbfGBBbCGHZhtQZtPHVSVHPV
    pBnWgBpzWzBbWDWvzwpgCvqcmLNTrdLmmrnRLTrmrTdmLd
    dRWWtRGBDPQctQDZ
    CMfnCfFMmTTFVCmfmTLvsnMPZjccHRcPrRPsZQQZPQDRrQ
    fLCffmRTCvRnmTllzVvpdpphNJggpGbJJJpdlh
    cfsfbbWqqZqDScbhGDPDjTPmpVtmmPjPntFD
    dCBBCRQCHNHLMQvrJJJBJLrGPMnjVtPjVMmnjPFmPmMpFn
    HvRGLddgHHvqhwzbWhcgcf
    DHmhhDDhbqrRhvbrHqhPbPvBwwWwTmMGTVwwBMGWVNNwVw
    gSzJjnnjsscfcsZcSfWwQGdMMcWTBcTTMTMw
    nsFCCFZsnSlCFBFflfflsztRPCqvvHPttRbHbhvPrrRr
    SGNRLzpNpgNSNNlWFNwzqJfHfJHBtBdJhBJrHd
    bQnjPTnjVdZtPPrfLP
    TTnTccLnjGFcDNggFl
    lBJmNzJlzmmQBlzgVVjLWRDfLjRsRLWWRS
    vTMvTvtrfdhrrrPTdZwTTwtDqSSnWFWDFMMsRssjjsRqWD
    ZpbvHbZddtbrdrdhcBmmpGQfBQgJNcmz
    qsmhTmTVcDdcffhPhPvvzFhF
    BNNlJbjWBWjtRtNljbmBBCpFzPlFPgpPfwFnlffnlPgg
    JtBmmrrrRbDSrVqDqVSD
    VmSgpSLgJjVDMrFrmMlfFmsG
    bZWhZbzWcCwTWPRCwwwSPQzwMHnflHnGsMGnTrsflGMnlfTF
    CqWhWqWCCWRRZQcLqDLpVDDLNStpgD
    JwzTVzzcLzVJVVlJpVTwzGcrWhFjqsBjCjQFjWcCCjCjBF
    MDSNQMtbSSnSbgbRDvnRNgvhmrqrjBCBtmCrCCWFqthhWm
    bgMfNSSNZTwZQddGJZ
    jjPgbFjjStjjPcSbrbtpvNrGnGDvBnMGvNDNGG
    LwdZwsTdWTTmwDNCNppGBsnpBR
    mWmHwZWhTTJWHBdQQWBjcFczSgjlSftjbtQPzg
    fCBlrffzlCzcmfLDlfgRRnHScsHvRSQHMQvs
    LthpZqtpVThZhGJtqTFvHsVnvsvMsggjHQjgQH
    ZWFWGTLTWBNCWfDfmD
    BpFqJrpcZZBDhDsNqMHhRG
    zwlzPdmzPLhwvhMgsHnN
    mPlWjlQfQjjllWtjQCWzCrBFJFTBcSNcJbbZfcZJSb
    FpzNFTThBDChnnzNBCBNDzWGndgWqbJWVGqmPbJqQgQm
    HQRctcvwgddVcJGd
    LwRMZLjQfSRjFNzFSrhNsThS
    WgRWVLWhFqgqgcgWHqLRWHVRbbbNwBmtBcNcwdwwBNwBzwBN
    pSfssjDSrSvJpNJCztbJBtMNbt
    TzsvGGnjGPsPjSTpDrPfPDSHhWqLgZZVHVhZZZZVQQFLnZ
    sHjtPjQTtDbsfrrqWR
    ZmccchvlvSvvZNMVNhvBLWRJJblWJWJlJfwHbwlL
    hMMBzSMppVSzSNNmNgHjPgGPFTCpTTgTgG
    rPQpCPPCQQZdcFhcZgzVJgwt
    vDBSHmvHSSMlDWWmljnHBvFwzzcrwhVSJwVFtzcJJghw
    bmmrsrsbsvNdCTdbPTdb
    NgzBnsBNnfjgNvvfvWbtShSSFSMLJMFjjbPh
    CCcRHdlQrQPLTJPLGP
    HrDlRcqcDJCCDJHlcplrDJDfgfzBWWNZfvsnNqWwngZWwW
    TggFVbjVTVzRwFFjjqBBqpzNztqcQqNNqN
    SnZPrWndmShSZSPsnSLsJhpMQpcJtNqBClltqctC
    mfWsGdnZZWmZmrLSfsrnmPggwtggRgGTVHvRwgFDjTGT
    wQMZFQwppbPHPbLQJsgQNJJmBnds
    qrSGvRRCvTzTDNnhNgmCgdBm
    qzRrcRlGjvvzlzrvjcGqSSzMHWwVdPMWFpFMHMHWlPWMVV
    BccsFzrBcsfpccccgFmQqlGNqCTLTlQnqgLlRG
    MZbVSMddddSPPtdHPVJPJdTllqLNLGGTNCCRhTnHTQHh
    DMwtbVZDZbSbpprcpccppwrq
    bSZTdNqFjjzjqQMQ
    WtRztLsWJpPLzrsMDlDQjMhVMC
    pcgHRpHmWPJgLRzBJHBPPRBfvvfGNGvvFwfvSvfTmNNZfN
    RjvBljWTTWTlqmBvHjGptRgccZcbPtcttbpzbn
    VhDSDdJLJpSNNVVznPzzzSzcZFngZt
    LsMdQNVMHsBHjqps
    rSPSLTnSCClfSFCR
    MwtZgwNBzzTjzZMbbmjNtwmBcRvFqQllqtQRlFRVqRqVVcvq
    TsMhZsjNjBbTbBbwZLpDnHdDGdHWnpGHnh
    LfdssTFBjFHSwlhzCcZZgMMfMhZZ
    DmtDvrpmNStVvqpPczrZMMhZhhChCC
    vJtJpQWvNVDtJFbHlJSlBbSF
    PgvHLbcgRcGGHzRvgGgchBzsCZnmNZmZddrCrvdddZCZrF
    JQBMjlStJrQsZFrNCZ
    JSJTlqMtjptlplSSqJVDBqphwhhwHRPRggGbGzHbgzVRPH
    pBsztsZdBsnWhntVnhtVTqTWNQlGGTGGFFlQFlTl
    fDvLSMcbDbfMrGqFqSGwSQPzqz
    zjcvLMMvffLjgggzfjjJCCLcdBtRphddBmmZBpCZZpZpmmhs
    gmmSDplcPHDfHDlbVNrtCtCCNqHvTn
    QGwJjzdPMzJhFLwnbvMqrTVCCMnvMv
    LQQZdGjGdJhFBFQGjLGBJzzsBSWcPflllcfmDmfBsmBWPs
    sMppbLDRMQbrTDTJjwcqnfnjwwnw
    CgmFgSPHPzHgdmJWZZFzCzZZcVnGGnttWtVVVnccfjVqwVff
    PhCChPSNgPgPJhPHhFZgsDNRspsvMNQLRvNDvpbD
    pHnVnlRGVpfgpfgCpCTz
    LCNQPqZCqNSSZCStzPgTmztwPfzwzT
    dbSqhjhqQhNLQLFjLjGvFcRnCcsvRVvvsFnG
    QTdnDTDQbCnMdbqdwSmJBljFJZhttJZMSh
    HfNfLNzGLsgWBpGJthlZFBSB
    ssRPHPfNsWHzZZvrvHfgLzNwnbbVVRwwRbVVcqDTddCVdD
    ZhZBrJssjrNsbRtWpjwjlmlm
    qTfHzGTfTGqqLGMdCCRcmmRRZcbPMccWmcRw
    dVCZdDHdGHQhBVNJSsBhBF
    NsplbGDbblHcbCpDlDGDPlPpJjdrVjgrvHnghvVdJrJdvvdn
    tQFMZNWSmWFQRzQSwtzFmwFFJdVBghVrnBjVjBgrvVjMvrhJ
    FFRmFZZRzQRmFLQSZZWQWFCPCNGcTCpGpfGfLTpblpff
    dnhQHqQCnqWwNQCwCRRdJjjJVVPmVVJsJP
    LccFRDgfMgLFBJVlmVVmgjrPzZ
    pvfGpRpSpTnTQbvbTv
    nwNlWwhWwNmJvQQdzdzZGMqDzn
    rcVscPfrLcfvjTFcQDqGdZqMzMSHZHjS
    LsfbbFcvVgRRblJRNR
    Rmrrlmltcffhllfl
    DDZMMMFZVsFsWZDSHhPPfgQbPPnQgcHctf
    zGMGSBVDWCrvwjtBvm
    DCZHwdDwNNGNZDZCjjtpTHLvtlgLbRLttlTL
    BzPzBJffJJQgLlRgGztQ
    PJffcJhJfNNSwFhGZC
    blplfHbwZSfbcbwSbfVSHDHcNvdrvrWsCrvWVCRWnvNndtWW
    hQzBTQLLMBWBsnsnNC
    zMsgTjqTqPpZHbfp
    ZLNNLtfZhRJQtpQhNRttZRqcGFcqGBzjrcqclGScFljjcj
    VnvVVPMWHwgJCMvwdcGFdFrGcwzGBG
    WHgPJJvWVTgnvgvTHmWCHmsNZRtNRDZpfZQffZZssR
    rZgMFMVVjGbVSqZbhftLRDmCGRCNDCtm
    cdddQzdWsWnQvzscnfRHmfnDNtLNChRmfH
    zplDQdDzwszlDsWdPcldzMFFrFrBFgpqVBZgSgZFrF
    gbzfbTvbJgbvzvTvJvJmzvjcBBQSfWDLCSBQQfLZWSCdBHHD
    GhnMhsGrMNPPwnwsRPNsFCZDWHZdWBWHdrZLdCHBBC
    PsnGsssGNsNRFsLGwMVNccJVmbjcjJblbzTVjlTz
    RfBqNfVmPLTTTVRZMMBWjlMvBgbbMs
    rHJzDwJdHzQgbMQlWMhd
    HtJrpCcHflLPFfpq
    SSGtmjQFStDbQbqGWJNnpZwgPsZjnJNNfZ
    RdClRMhlldHdlvdThNgwfJNRspNwJpwZcN
    CMBLgvvBLhrrBHTCVVLBQrWtbrDGmbtWGtzDQFGb
    ZRRCqHpRdztLSqWz
    hRsGjMVccGshPVDVcBmfgFFzggFgfBmWWtSd
    PVjlGhcGJjGPsGMjjDVrMTpRnvRZlHnZNNQbHppTTC
    QjbjWWlndRbwwwQWQdtpTVVZtzRPhHDzThhP
    fFfLsvrLCrmvGlSfLSrlPHThDzTztHVtzDzGtTVh
    fvrgsfccFLLLSvfMCCSWBjdMqdqNWBdBQNnWld
    pjGPvvbllvqGvGjwMbpbRmgSmSwtRtShgSSWWQmW
    DffTZLLzFFFTCRJMhWBzhmhgJt
    CVVZLLTTsZTFCnMffMTFHffLVpGpNqGjNbNPljqVPcGNGlcv
    rvJfztqQJqqrqHHwCzClTbBhDBBDrbSgphbVTrrV
    FFGdNWFLGWMmLDbpRwVbgRBp
    dmNGmZjMjQwtHCCw
    MSGbqbqMbbGDhSSGDhLNBPNcrDPPfzfczPfrnv
    rRwsrljslRgslwwgpssCjRtBNvnmPmBmccvmPnmzcP
    CjVjsssJQpTCrCCgHJLMSZbhMhWGZhHG
    JtBGBFGRVGVLLctRttthLFRBDQlDppljJwNQlpHHQqDHbHHN
    szSZTrzdzTSMdzsmsqqQqwdbgDNqgQNjlN
    SzmrlWPrlfPMsMZTPtCVCFRhhvhccLFPvL
    chmbsMDMMcBnGbZBzZGL
    JjjgJrJJggNgtrQSQBLjWBZlGnnB
    rFNrFtFCRNVrrsHmMDcsLqMhMF
    DLMwrBGgrBBDrcBcNBgWhpGhqVhhqSjqqqmjjp
    JfJCZCdtlbZlHHbjWVpPmHWb
    lTtZtnFQCztWFzJflZLcsrLFNDRNBcswcLMM
    tsVttVCBsCcCqPqwvtqNPQjWDDWjzQQWnpJQSrWJJN
    LMRdZvHGgMmZZGGLGLbhLhJpjWrSjndWJpSJrJppJnpj
    RhRgLMlmgMGMFGLlTtcBvwcfFCtVvfFT
    llBBmtncBglfqwsdwsjdbHwQHm
    zGRhPJvFzhvFvMDGFvGPwSWWQpdpHSwSjWjHsWMw
    JGFhDPrLTzhLhvFPzPjLjTzFlBqltnCqqBffcTgfnZfCfCnB
    QWQGNHQBffCNDMPdRTDLPVMN
    hrtlhtzZwJtwwgFgtlJthJJtZMqPVVpLmDVqRDmVPmMPVRTV
    SJSSnJwJlsFFvcHSBbfLQHGL
    zMNVzhNFsdNssmhlvtQvlttBlVGbTt
    wHpFwjHjLFHlwtncTQlnwn
    CCpjpqLCLqJggghqRmFsNRFmPszh
    CTjmprmNcnmCNVQbstnstvbnQv
    jqqdGhHgPRdfRRQvfLQffz
    BGgqBhMdgqBHMDDqqMPwgdhSTJNjjmJWWCTCCmwTWZNpmZ
    LqSDFFmdqDBDbbBHWl
    dQcRpgwRQPngBWHbVWht
    vdQMJQCQQvrJNqrTNsSZSmZT
    cqrHcHHFNFPLLNPHLWnHHFHFjvlbZfWjSjBjZfSblhbBSbSf
    VdVTGwTzTwTpMJslvjfsbbZSvnBClj
    nJdGmmTpTTdmNQNNLPPmDFQL
    VcjpTTtpcbThJBTTcjBvSPJzJlvPlwfJrgrgvw
    dmshCNqnqdhmRsCsqCnrNgPPrPfzrNLfSwSgzl
    ddQZdFnhsMttFTbDDD
    hLThMTSdfMzzLzTLsFTbwtDvtsFTnttF
    pZlNllPWrPCQPQlWNqjrqrjsnfDFbbtbstwswtvDnwtwjt
    pCNNQBCClQrplrfHQpQdSRMSVRhJzRchRhGHSh
    RZfVfRnTcPQWZcRVcRNSvljQsSSjNvvNqvss
    pwbwgmqJhGlNvvzgMM
    pthrhpmthmwhHHpLbbdrJmLWWRPTRZnCnPCPBnWTWTZq
    nzsJJsMjGMMsQFbnNmLnmCfb
    HlllPPTPlWTPDRRDRHcwwhrLLZfQmgmmQCFHvNZCgfggZQ
    lPcNTwNWddGsMSttdjzj
    bTbJZJDVFdqpBZTFTZJprdcsjjGszmjQszcjzDvsgccv
    HHLnhMNCCPhfhCLMlPlnvgGfctscSvtSjGjztQtm
    wWNQMPPMwlPHHPnnRLwWpJbrbbqbdppqJVJdZV
    gpgpNnnrhwBVWFqgjqqF
    ZCTsdRbCGZpZzCSGbWmtMBmWtqBBqdjjmq
    LcsTSlplZrJNcQQPvH
    DJDpMcqJDcDddNcJPGcJGFfnfZZmZZfRQZRNmVQVnBNn
    WWThvSSHFshlSsHvgTHLlzBQfVWwmwnnfmRQWnQVRn
    vljlgHjhstHbLhjLjrbPqMGJcJFJCMPrdF
    pJlPMpMBrrMcnrBBMMrvdhdgFvmcFdWtmdtftg
    bVVRmTRVTVVSVVVzZZqsggFHWTWtWffWFfWdtHdH
    RjLQDSzmVsjRRZSZQrCMJDPwlnJJnGGrPB
    GRgJtglPGlCrhQQrfW
    vSjvZvZHNBjZSvwjvmvNDqhhFqhHrMhrHpfWqQfhpW
    NBmwDSbZbvsNNBjmZfJzJgbVnGJbzVzPVRtL
    QQZVQCdlVmdZnWmJBrLwSJRdggwdJr
    DqHhPhcpvDqPFjhHjtFPssSJNfSBwBvffrgJwwJLBJbr
    jpsHcptHsqtPttsjctpFhGlZwCMlWMmWGCWVQnTMVWmz
    zzvnHjHWSfnvzpnfSRHdgrrsmWmhVrrwwbTrTmsrwm
    NlLNGqqclqlZZCLCGGCPllJhhPJbrmpmrwVshFVsJbwr
    DMLDNCZQCGLQGDtQpGtMjRdfdSRSSBtjtBjfjgjS
    WBBJfjBQJjftGCbttVJptC
    sdlmTHsqNsqpRtGcbWdbnR
    svNMhmWHNmvmvHZWTvBQDgSSjDFPSBSFPP
    mrlgqncgwHdqWWhRNtRttSvbRwwSvN
    jZLCZVZzVcCTcpCDVSBBSbvvRFbfDBSDNS
    VPJVpQjTZLjVLJJQGZTZGcQVghsGlmhshlqnghqrdrgldhlW
    qwGDMqMFWbFbqbDwMgqgnjfnffffcTTjNnpTfBncZZ
    lSdclSltlzPsJlhNNfTQPTmQhpPh
    LtllsscLLLvvJlSzWGqwrDGqvbqMwMGb
    bnfvnnQsVTdHQDmdNN
    fwlttwrfSfLwdGZGmGND
    qlPrjjplfSpjSltfttBBsvgzgbgnggBqWVvC
    HHrBSmBqBHdHCFcQsc
    WqtMDDnMMZhMhcsQVM
    bqtbGDLLTTnRPwvGgBNmNrwvjm
    RwWzWFwBcdSdMgJW
    mrTjVQDQthQvjrvTmggFbbdgSJcJDFgSdg
    phhFtvjNvFvTTvmTHZzlGlzZRZLBlzRp
    cHlZmZmJSHZcTVGmvZVcGVHlNtbDNbtWFWdtlbCWbWtQDFdr
    pwfwgRhngPjjtdWdNtFrdFwT
    RTgMpfhBcMJGHqcz
    dmCbpCLQVFmCRddCTFLCgZtWPNBhSNNwZWgWZvSwhN
    rDzGDjJHhhwzThBS
    rJrclsjjsJMJnJMLnbQQQFmRTqLVRT
    TgFTGPtrWHzgCJDz
    fQbfLwbbFbWjHJHWjd
    LvQvwVfQvQhLLLLsmMBfLfBhqchtGFSNSFNqrSqPcqSGttSN
    MsMmFgTVMMgMdFVMhdzWqCCBCWjWqBWqhzzL
    nHfZDlbvcrDpcpcfDJbNJqGLbCjBmjLzJC
    RnDZHwmmTwVMTsMS
    NpdpdnjNCRjBnHRPpBDnhSdSwFFLFdScFFWcFWLd
    stqMrMZtsQMJmMqvrtqMbstQVchPFWgFVVFVwfWJcSSWVSSV
    MQrZzbvMrrrZZtZmstMtMsDRRjPHCGTjBnzpnDHTCGjG
    ClGGvDMGMNhNSmFVPbpgFgmFgZPb
    WhTTLWsBBWTcrPpggtQpsQFpVb
    fBRTqWLjTrzDNhMvCSGDzl
    jbzjttVzpbWzWVbTtzWzVwPbPPcwDDccccFNFLMFsm
    rlghJZHmCHvHJvRLSNLhhhccNPwcMP
    QffmBZRBGtVTBdTG
    bDRqHwwRpNPnbppn
    FqrlQSQJSVsQrSCmpdPNpcNCcP
    jFZjrssjBhrZVhJLLGHfMHfwtqRjMqRTvD
    SMMCTBzFfSRhTThCSMRSzzHnJgqGDHQgGVDnqrqnqqnqcQ
    ljvjpWPbrWWtrbmDVDlmnDnqVmgJ
    bNjsNPrNPtswfZzfTSTBwhSM
    hNMNdssdMqdTQchqgNZzHtwmwGHHzmQZGHDH
    JrbJvJrvLrPjrPCVCjRBLPbzfRFZmzwHgFHmGHmwFwzFZR
    rpbrvVjPCBbCJrCSLSPsTlphNncMglcNTMWMql
    fGWGHbrllCCWWllFNPQSZvdPSvdZTffZ
    tqssVjJMJWzWVvSvWS
    qtwMwDWjnRRwssWjngwjjnhhDcCLhhCFHlcmcbCHFmDC
    rFTdFjdRDTTlDWCqvhwLhwZdLS
    QzfJfnfsbsJHMnNmHhVpCZwSQSWqSqVQhS
    nJnczsHzNMmBJnbnbNwnfzJfcDTltllRTgPlFlgPrTDjPRGl
    ZLCGDvvJlvGChSPZWPSsZWdRRN
    rQccBwcccnHmQggnVLPLWpgVWPpWzSRs
    fmwTfTHnMBTfJDbfftJLvhlL
""".trimIndent()