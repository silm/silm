//:://////////////////////////////////////////////////
//:: X0_D2_THREATIMPOSSIBLE
//:: Copyright (c) 2002 Floodgate Entertainment
//:://////////////////////////////////////////////////
/*
An almost-impossible threaten check for the speaker. Compares hit dice
with the object and adds a bonus for high charisma. 
 */
//:://////////////////////////////////////////////////
//:: Created By: Naomi Novik
//:: Created On: 09/26/2002
//:://////////////////////////////////////////////////

#include "x0_i0_common"

int StartingConditional()
{
    SetThreaten(GetPCSpeaker());
    return ThreatenCheck(DIFFICULTY_IMPOSSIBLE, GetPCSpeaker());
}
